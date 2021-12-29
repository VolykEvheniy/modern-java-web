package com.example.project.controller.command.receivers;

import com.example.project.controller.exception.*;
import com.example.project.domain.Category;
import com.example.project.domain.enums.RoleEnum;
import com.example.project.domain.User;
import com.example.project.model.dao.CategoryDAO;
import com.example.project.model.dao.DaoClass;
import com.example.project.model.dao.DaoFactory;
import com.example.project.model.dao.UserDAO;

import java.util.List;
import java.util.UUID;

import static com.example.project.controller.util.ValidationUtils.verifyUserHasRole;
import static com.example.project.controller.util.ValidationUtils.verifyUserNotNull;

public class CategoryReceiver {
    private static CategoryReceiver categoryReceiver;
    private CategoryDAO categoryDAO;
    private UserDAO userDAO;

    private CategoryReceiver() {
        DaoFactory factory = DaoFactory.getInstance();
        categoryDAO = (CategoryDAO) factory.getDAO(DaoClass.CATEGORY_DAO);
        userDAO = (UserDAO) factory.getDAO(DaoClass.USER_DAO);
    }

    public static CategoryReceiver getInstance() {
        if (categoryReceiver == null) {
            categoryReceiver = new CategoryReceiver();
        }
        return categoryReceiver;
    }

    public List<Category> getAllCategories(UUID userId) {
        User user = userDAO.findById(userId);
        verifyUserNotNull(user);
        verifyUserHasRole(user, RoleEnum.ADMIN);

        return categoryDAO.findAll();
    }

    public Category createCategory(UUID userId, String categoryName) {
        User user = userDAO.findById(userId);
        verifyUserNotNull(user);
        verifyUserHasRole(user, RoleEnum.ADMIN);
        if (categoryName == null) {
            throw new InvalidCategoryNameException();
        }

        Category categoryToCreate = new Category();
        categoryToCreate.setName(categoryName);

        return categoryDAO.create(categoryToCreate);
    }

    public void deleteCategory(UUID userId, UUID categoryId) {
        User user = userDAO.findById(userId);
        verifyUserNotNull(user);
        verifyUserHasRole(user, RoleEnum.ADMIN);
        if (categoryId == null) {
            throw new InvalidIdException();
        }

        boolean deleted = categoryDAO.delete(categoryId);
        if (!deleted) {
            throw new FailedToDeleteCategoryException();
        }
    }

}
