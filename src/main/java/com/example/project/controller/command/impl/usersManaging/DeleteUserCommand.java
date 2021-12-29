package com.example.project.controller.command.impl.usersManaging;

import com.example.project.controller.command.Command;
import com.example.project.controller.command.receivers.UserReceiver;
import com.example.project.controller.util.constants.ViewJsp;
import com.example.project.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

public class DeleteUserCommand implements Command {

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User admin = (User) request.getSession().getAttribute("user");
        if (admin == null) {
            throw new RuntimeException("Дані сесії неправильні");
        }
        String userToDeleteId = request.getParameter("userId");
        if (userToDeleteId == null || userToDeleteId.isEmpty()) {
            throw new RuntimeException("Id користувача для видалення не надіслано");
        }

        UserReceiver userReceiver = UserReceiver.getInstance();
        userReceiver.deleteUser(admin.getId(), UUID.fromString(userToDeleteId));
        List<User> users = userReceiver.getAllUsersForAdmin(admin.getId());

        request.getSession().setAttribute("users", users);
        return ViewJsp.USERS_ADMIN_JSP.getPath();
    }
}
