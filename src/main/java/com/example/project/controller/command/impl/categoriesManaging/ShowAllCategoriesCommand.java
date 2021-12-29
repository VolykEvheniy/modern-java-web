package com.example.project.controller.command.impl.categoriesManaging;

import com.example.project.controller.command.Command;
import com.example.project.controller.command.receivers.CategoryReceiver;
import com.example.project.controller.exception.InvalidSessionUserException;
import com.example.project.controller.util.constants.ViewJsp;
import com.example.project.domain.Category;
import com.example.project.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowAllCategoriesCommand implements Command {

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new InvalidSessionUserException();
        }

        List<Category> categories = CategoryReceiver.getInstance().getAllCategories(user.getId());
        request.getSession().setAttribute("categories", categories);
        return ViewJsp.CATEGORIES_ADMIN_JSP.getPath();
    }
}
