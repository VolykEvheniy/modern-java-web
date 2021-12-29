package com.example.project.controller.command.receivers;

import com.example.project.controller.command.receivers.dto.UserReportDto;
import com.example.project.controller.exception.UserNotFoundException;
import com.example.project.model.dao.DaoClass;
import com.example.project.model.dao.DaoFactory;
import com.example.project.model.dao.RoleDAO;
import com.example.project.model.dao.UserDAO;
import com.example.project.domain.Role;
import com.example.project.domain.enums.RoleEnum;
import com.example.project.domain.User;
import com.example.project.utils.PasswordEncryptor;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.project.controller.util.ValidationUtils.verifyUserHasRole;
import static com.example.project.controller.util.ValidationUtils.verifyUserNotNull;

public class UserReceiver {
    private static UserReceiver userReceiver;
    private final UserDAO userDAO;
    private final RoleDAO roleDAO;

    private UserReceiver() {
        DaoFactory factory = DaoFactory.getInstance();
        this.userDAO = (UserDAO) factory.getDAO(DaoClass.USER_DAO);
        this.roleDAO = (RoleDAO) factory.getDAO(DaoClass.ROLE_DAO);
    }

    public static UserReceiver getInstance() {
        if (userReceiver == null) {
            userReceiver = new UserReceiver();
        }
        return userReceiver;
    }

    public User register(String login, String password) {
        User selectedUser = userDAO.getByLogin(login);
        if (selectedUser != null) {
            throw new UserNotFoundException();
        }

        String salt = PasswordEncryptor.getSalt();
        String hashedPassword = PasswordEncryptor.getHashedPassword(password, salt);

        Role role = roleDAO.getByName(RoleEnum.USER.toString());
        User user = new User();
        user.setLogin(login);
        user.setSalt(salt);
        user.setHashedPassword(hashedPassword);
        user.setRole(role);

        return userDAO.create(user);
    }

    public User login(String login, String password) {
        User user = userDAO.getByLogin(login);
        if (user == null) {
            throw new UserNotFoundException();
        }

        String generatedHash = PasswordEncryptor.getHashedPassword(password, user.getSalt());
        if (!user.getHashedPassword().equals(generatedHash)) {
            throw new RuntimeException("Пароль не правильний");
        }

        return user;
    }

    public List<User> getAllUsersForAdmin(UUID adminId) {
        verifyUserAccessByRole(adminId, RoleEnum.ADMIN);
        List<User> users = userDAO.findAll();

        return users.stream()
                .filter(user -> !user.getId().equals(adminId))
                .collect(Collectors.toList());
    }

    public void verifyUserAccessByRole(UUID userId, RoleEnum role) {
        User user = userDAO.findById(userId);
        verifyUserNotNull(user);
        verifyUserHasRole(user, role);
    }

    public void deleteUser(UUID adminId, UUID userToDeleteId) {
        verifyUserAccessByRole(adminId, RoleEnum.ADMIN);
        boolean deleted = userDAO.delete(userToDeleteId);
        if (!deleted) {
            throw new RuntimeException("Помилка при видаленні користувача");
        }
    }

    public void restoreUser(UUID adminId, UUID userToRestoreId) {
        verifyUserAccessByRole(adminId, RoleEnum.ADMIN);
        boolean restored = userDAO.restore(userToRestoreId);
        if (!restored) {
            throw new RuntimeException("Помилка при відновленні користувача");
        }
    }

    public List<UserReportDto> getUsersReport(UUID adminId) {
        User user = userDAO.findById(adminId);
        verifyUserNotNull(user);
        verifyUserHasRole(user, RoleEnum.ADMIN);

        return userDAO.selectUsersReportData().stream()
                .filter(userReport -> !userReport.getId().equals(adminId))
                .collect(Collectors.toList());
    }
}
