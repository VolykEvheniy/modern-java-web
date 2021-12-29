package com.example.project.controller.command.impl.usersManaging;

import com.example.project.controller.command.Command;
import com.example.project.controller.command.receivers.UserReceiver;
import com.example.project.controller.exception.InvalidSessionUserException;
import com.example.project.controller.util.constants.ViewJsp;
import com.example.project.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

public class RestoreUserCommand implements Command {

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User admin = (User) request.getSession().getAttribute("user");
        if (admin == null) {
            throw new InvalidSessionUserException();
        }
        String userToRestoreId = request.getParameter("userId");
        if (userToRestoreId == null || userToRestoreId.isEmpty()) {
            throw new RuntimeException("Id користувача для відновлення не надіслано");
        }

        UserReceiver userReceiver = UserReceiver.getInstance();
        userReceiver.restoreUser(admin.getId(), UUID.fromString(userToRestoreId));
        List<User> users = userReceiver.getAllUsersForAdmin(admin.getId());

        request.getSession().setAttribute("users", users);
        return ViewJsp.USERS_ADMIN_JSP.getPath();
    }
}
