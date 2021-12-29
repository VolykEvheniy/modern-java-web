package com.example.project.controller.command.impl.auth;

import com.example.project.controller.command.Command;
import com.example.project.controller.util.constants.ViewJsp;
import com.example.project.domain.User;
import com.example.project.controller.command.receivers.UserReceiver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.example.project.controller.util.ValidationUtils.isLoginCorrect;
import static com.example.project.controller.util.ValidationUtils.isPasswordCorrect;

public class RegisterCommand implements Command {

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (!isLoginCorrect(login) || !isPasswordCorrect(password)) {
            throw new RuntimeException("Дані авторизації не правильні");
        }

        User user = UserReceiver.getInstance().register(login, password);

        request.getSession().setAttribute("user", user);
        return ViewJsp.INDEX_JSP.getPath();
    }
}
