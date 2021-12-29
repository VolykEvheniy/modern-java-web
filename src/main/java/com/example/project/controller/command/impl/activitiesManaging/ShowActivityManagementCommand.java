package com.example.project.controller.command.impl.activitiesManaging;

import com.example.project.controller.command.Command;
import com.example.project.controller.command.receivers.ActivityReceiver;
import com.example.project.controller.command.receivers.UserReceiver;
import com.example.project.controller.exception.InvalidPinActivityRequestDataException;
import com.example.project.controller.exception.InvalidSessionUserException;
import com.example.project.controller.exception.InvalidShowActivityManagementRequestException;
import com.example.project.controller.util.constants.ViewJsp;
import com.example.project.domain.User;
import com.example.project.domain.User2Activity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

public class ShowActivityManagementCommand implements Command {

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new InvalidSessionUserException();
        }
        String activityId = request.getParameter("activityId");

        if (activityId == null) {
            throw new InvalidShowActivityManagementRequestException();
        }

        List<User2Activity> user2Activities = ActivityReceiver.getInstance().getUser2ActivitiesForAdmin(user.getId(), UUID.fromString(activityId));
        List<User> users = UserReceiver.getInstance().getAllUsersForAdmin(user.getId());
        request.getSession().setAttribute("user2activities", user2Activities);
        request.getSession().setAttribute("activityId", activityId);
        request.getSession().setAttribute("users", users);
        return ViewJsp.ACTIVITY_MANAGEMENT_ADMIN_JSP.getPath();
    }
}
