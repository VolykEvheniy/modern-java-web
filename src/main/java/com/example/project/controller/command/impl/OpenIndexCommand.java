package com.example.project.controller.command.impl;

import com.example.project.controller.command.Command;
import com.example.project.controller.util.constants.ViewJsp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class OpenIndexCommand implements Command {

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return ViewJsp.INDEX_JSP.getPath();
    }
}
