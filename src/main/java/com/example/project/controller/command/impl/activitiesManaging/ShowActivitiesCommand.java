package com.example.project.controller.command.impl.activitiesManaging;

import com.example.project.controller.command.Command;
import com.example.project.controller.command.receivers.ActivityReceiver;
import com.example.project.controller.command.receivers.CategoryReceiver;
import com.example.project.controller.exception.InvalidSessionUserException;
import com.example.project.controller.util.constants.ViewJsp;
import com.example.project.domain.Activity;
import com.example.project.domain.Category;
import com.example.project.domain.User;
import com.example.project.domain.enums.ActivityFilterCriteria;
import com.example.project.domain.enums.ActivitySortCriteria;
import com.example.project.domain.enums.SortOrder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowActivitiesCommand implements Command {

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new InvalidSessionUserException();
        }

        String sortCriteriaName = request.getParameter("sortCriteria");
        String sortOrderName = request.getParameter("sortOrder");
        String filterCriteriaName = request.getParameter("filterCriteria");
        String filterValue = request.getParameter("filterValue");

        ActivitySortCriteria sortCriteria = sortCriteriaName == null
                ? null
                : ActivitySortCriteria.valueOf(sortCriteriaName);
        SortOrder sortOrder = sortOrderName == null
                ? null
                : SortOrder.valueOf(sortOrderName);
        ActivityFilterCriteria filterCriteria = filterCriteriaName == null || filterCriteriaName.equals("")
                ? null
                : ActivityFilterCriteria.valueOf(filterCriteriaName);

        List<Activity> activities = ActivityReceiver.getInstance().getAllActivitiesForAdmin(
                user.getId(),
                sortCriteria,
                sortOrder,
                filterCriteria,
                filterValue
        );
        List<Category> categories = CategoryReceiver.getInstance().getAllCategories(user.getId());

        request.getSession().setAttribute("activities", activities);
        request.getSession().setAttribute("categories", categories);
        return ViewJsp.ACTIVITIES_ADMIN_JSP.getPath();
    }
}
