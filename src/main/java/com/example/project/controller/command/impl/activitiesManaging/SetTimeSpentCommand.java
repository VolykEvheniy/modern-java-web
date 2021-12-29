package com.example.project.controller.command.impl.activitiesManaging;

import com.example.project.controller.command.Command;
import com.example.project.controller.command.receivers.ActivityReceiver;
import com.example.project.controller.command.receivers.dto.UserActivityDto;
import com.example.project.controller.exception.InvalidSessionUserException;
import com.example.project.controller.exception.InvalidSetTimeSpentRequestDataException;
import com.example.project.controller.util.constants.ViewJsp;
import com.example.project.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

public class SetTimeSpentCommand implements Command {

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new InvalidSessionUserException();
        }

        String userToActivityId = request.getParameter("userToActivityId");
        String timeSpent = request.getParameter("timeSpent");
        if (userToActivityId == null || timeSpent == null) {
            throw new InvalidSetTimeSpentRequestDataException();
        }
        BigInteger timeSpentBigInt = new BigInteger(timeSpent);

        ActivityReceiver.getInstance().setTimeSpent(user.getId(), UUID.fromString(userToActivityId), timeSpentBigInt);

        List<UserActivityDto> activityDtos = ActivityReceiver.getInstance().getUser2ActivitiesForUser(user.getId());

        request.getSession().setAttribute("userActivities", activityDtos);
        return ViewJsp.USER_HOME_JSP.getPath();
    }
}
