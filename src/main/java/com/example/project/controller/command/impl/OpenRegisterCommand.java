package com.example.project.controller.command.impl;

import com.example.project.controller.command.Command;
import com.example.project.controller.util.constants.ViewJsp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OpenRegisterCommand implements Command {

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return ViewJsp.REGISTER_JSP.getPath();
    }
}
