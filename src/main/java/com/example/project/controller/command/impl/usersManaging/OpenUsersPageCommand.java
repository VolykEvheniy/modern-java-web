package com.example.project.controller.command.impl.usersManaging;

import com.example.project.controller.command.Command;
import com.example.project.controller.command.receivers.UserReceiver;
import com.example.project.controller.util.constants.ViewJsp;
import com.example.project.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class OpenUsersPageCommand implements Command {

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User admin = (User) request.getSession().getAttribute("user");
        if (admin == null) {
            throw new RuntimeException("Дані сесії неправильні");
        }
        List<User> users = UserReceiver.getInstance().getAllUsersForAdmin(admin.getId());

        request.getSession().setAttribute("users", users);
        return ViewJsp.USERS_ADMIN_JSP.getPath();
    }
}
