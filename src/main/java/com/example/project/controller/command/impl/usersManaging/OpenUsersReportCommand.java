package com.example.project.controller.command.impl.usersManaging;

import com.example.project.controller.command.Command;
import com.example.project.controller.command.receivers.UserReceiver;
import com.example.project.controller.command.receivers.dto.UserReportDto;
import com.example.project.controller.exception.InvalidSessionUserException;
import com.example.project.controller.util.constants.ViewJsp;
import com.example.project.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class OpenUsersReportCommand implements Command {

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User admin = (User) request.getSession().getAttribute("user");
        if (admin == null) {
            throw new InvalidSessionUserException();
        }
        List<UserReportDto> userReports = UserReceiver.getInstance().getUsersReport(admin.getId());

        request.getSession().setAttribute("userReports", userReports);
        return ViewJsp.REPORT_ADMIN_JSP.getPath();
    }
}
