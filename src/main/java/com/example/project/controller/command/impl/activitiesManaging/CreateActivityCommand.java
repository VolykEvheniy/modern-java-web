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

public class CreateActivityCommand implements Command {

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new InvalidSessionUserException();
        }
        String activityName = request.getParameter("activityName");
        String categoryId = request.getParameter("categoryId");
        String description = request.getParameter("description");
        if (activityName == null || categoryId == null) {
            throw new InvalidCreateActivityRequestDataException();
        }

        ActivityReceiver.getInstance().createActivity(
                user.getId(),
                activityName,
                UUID.fromString(categoryId),
                description
        );

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
