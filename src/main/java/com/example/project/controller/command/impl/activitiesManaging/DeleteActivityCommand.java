package com.example.project.controller.command.impl.activitiesManaging;

import com.example.project.controller.command.Command;
import com.example.project.controller.command.receivers.ActivityReceiver;
import com.example.project.controller.command.receivers.CategoryReceiver;
import com.example.project.controller.exception.InvalidCreateActivityRequestDataException;
import com.example.project.controller.exception.InvalidSessionUserException;
import com.example.project.controller.util.constants.ViewJsp;
import com.example.project.domain.Activity;
import com.example.project.domain.Category;
import com.example.project.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

public class DeleteActivityCommand implements Command {

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new InvalidSessionUserException();
        }
        String activityId = request.getParameter("activityId");
        if (activityId == null || activityId.isEmpty()) {
            throw new InvalidCreateActivityRequestDataException();
        }

        ActivityReceiver.getInstance().deleteActivity(user.getId(), UUID.fromString(activityId));

        List<Activity> activities = ActivityReceiver.getInstance().getAllActivitiesForAdmin(user.getId(),
                null,
                null,
                null,
                null
        );
        List<Category> categories = CategoryReceiver.getInstance().getAllCategories(user.getId());

        request.getSession().setAttribute("activities", activities);
        request.getSession().setAttribute("categories", categories);
        return ViewJsp.ACTIVITIES_ADMIN_JSP.getPath();
    }
}
