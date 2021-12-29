package com.example.project.controller;

import com.example.project.controller.util.constants.ViewJsp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/ErrorListener")
public class ErrorListener extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processError(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processError(request, response);
    }

    private void processError(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        request.setAttribute("error", throwable.getMessage());
        try {
            request.getRequestDispatcher(ViewJsp.ERROR_JSP.getPath()).forward(request, response);
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}