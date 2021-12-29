package com.example.project.controller.command.impl.usersManaging;

import com.example.project.controller.command.Command;
import com.example.project.controller.command.receivers.ActivityReceiver;
import com.example.project.controller.command.receivers.dto.UserActivityDto;
import com.example.project.controller.exception.InvalidSessionUserException;
import com.example.project.controller.util.constants.ViewJsp;
import com.example.project.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class OpenUserActivitiesCommand implements Command {

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new InvalidSessionUserException();
        }
        List<UserActivityDto> activityDtos = ActivityReceiver.getInstance().getUser2ActivitiesForUser(user.getId());

        request.getSession().setAttribute("userActivities", activityDtos);
        return ViewJsp.USER_HOME_JSP.getPath();
    }
}
