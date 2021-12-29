package com.example.project.controller.command.impl.auth;

import com.example.project.controller.command.Command;
import com.example.project.controller.util.constants.ViewJsp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutCommand implements Command {

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        return ViewJsp.INDEX_JSP.getPath();
    }
}
