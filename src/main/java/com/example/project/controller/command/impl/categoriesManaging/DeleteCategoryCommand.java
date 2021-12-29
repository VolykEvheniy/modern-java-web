package com.example.project.controller.command.impl.categoriesManaging;

import com.example.project.controller.command.Command;
import com.example.project.controller.command.receivers.CategoryReceiver;
import com.example.project.controller.exception.InvalidIdException;
import com.example.project.controller.exception.InvalidSessionUserException;
import com.example.project.controller.util.constants.ViewJsp;
import com.example.project.domain.Category;
import com.example.project.domain.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

public class DeleteCategoryCommand implements Command {

    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new InvalidSessionUserException();
        }
        String categoryIdParameter = request.getParameter("categoryId");
        if (categoryIdParameter == null) {
            throw new InvalidIdException();
        }
        UUID categoryId = UUID.fromString(categoryIdParameter);

        CategoryReceiver.getInstance().deleteCategory(user.getId(), categoryId);

        List<Category> categories = CategoryReceiver.getInstance().getAllCategories(user.getId());
        request.getSession().setAttribute("categories", categories);
        return ViewJsp.CATEGORIES_ADMIN_JSP.getPath();
    }
}
