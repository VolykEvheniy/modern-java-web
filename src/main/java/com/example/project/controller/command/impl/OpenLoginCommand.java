package com.example.project.controller.command.impl;

import com.example.project.controller.command.Command;
import com.example.project.controller.util.constants.ViewJsp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OpenLoginCommand implements Command {

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return ViewJsp.LOGIN_JSP.getPath();
    }
}
