package com.example.project.controller;

import com.example.project.controller.command.Command;
import com.example.project.controller.command.CommandList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "MainController", urlPatterns = {"/main/*"})
public class MainController extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        handleRequest(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        handleRequest(request, response);
    }

    private void handleRequest(HttpServletRequest request, HttpServletResponse response) {
        String commandName = request.getParameter("command");

        Command command = CommandList.valueOf(commandName).getCommand();
        String goTo = command.execute(request, response);
        try {
            request.getRequestDispatcher(goTo).forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
        }
    }

}
