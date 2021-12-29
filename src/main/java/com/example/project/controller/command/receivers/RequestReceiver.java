package com.example.project.controller.command.receivers;

import com.example.project.controller.exception.CategoryNotFoundException;
import com.example.project.controller.exception.FailedToDeleteCategoryException;
import com.example.project.controller.exception.InvalidCreateActivityRequestDataException;
import com.example.project.controller.exception.InvalidIdException;
import com.example.project.domain.AddActivityRequest;
import com.example.project.domain.Category;
import com.example.project.domain.User;
import com.example.project.domain.enums.RoleEnum;
import com.example.project.model.dao.*;

import java.util.List;
import java.util.UUID;

import static com.example.project.controller.util.ValidationUtils.verifyUserHasRole;
import static com.example.project.controller.util.ValidationUtils.verifyUserNotNull;

public class RequestReceiver {
    private static RequestReceiver requestReceiver;
    private AddActivityRequestDAO addActivityRequestDAO;
    private ActivityDAO activityDAO;
    private User2ActivityDAO user2ActivityDAO;
    private CategoryDAO categoryDAO;
    private UserDAO userDAO;

    private RequestReceiver() {
        DaoFactory factory = DaoFactory.getInstance();
        addActivityRequestDAO = (AddActivityRequestDAO) factory.getDAO(DaoClass.ADD_ACTIVITY_REQUEST_DAO);
        activityDAO = (ActivityDAO) factory.getDAO(DaoClass.ACTIVITY_DAO);
        user2ActivityDAO = (User2ActivityDAO) factory.getDAO(DaoClass.USER2ACTIVITY_DAO);
        categoryDAO = (CategoryDAO) factory.getDAO(DaoClass.CATEGORY_DAO);
        userDAO = (UserDAO) factory.getDAO(DaoClass.USER_DAO);
    }

    public static RequestReceiver getInstance() {
        if (requestReceiver == null) {
            requestReceiver = new RequestReceiver();
        }
        return requestReceiver;
    }

    public List<AddActivityRequest> getAllAddActivityRequestsForAdmin(UUID userId) {
        User user = userDAO.findById(userId);
        verifyUserNotNull(user);
        verifyUserHasRole(user, RoleEnum.ADMIN);

        return addActivityRequestDAO.findAll();
    }

    public AddActivityRequest createAddActivityRequest(UUID userId, String activityName, UUID categoryId, String description, String note) {
        User user = userDAO.findById(userId);
        verifyUserNotNull(user);
        verifyUserHasRole(user, RoleEnum.ADMIN);
        if (activityName == null || categoryId == null || description == null) {
            throw new InvalidCreateActivityRequestDataException();
        }
        Category category = categoryDAO.findById(categoryId);
        if (category == null) {
            throw new CategoryNotFoundException();
        }
        AddActivityRequest request = new AddActivityRequest();
        request.setName(activityName);
        request.setCategory(category);
        request.setDescription(description);
        request.setUser(user);
        request.setNote(note == null ? "" : note);

        AddActivityRequest createdRequest = addActivityRequestDAO.create(request);
        createdRequest.setCategory(category);

        return createdRequest;
    }

    public void deleteAddActivityRequest(UUID userId, UUID requestId) {
        User user = userDAO.findById(userId);
        verifyUserNotNull(user);
        verifyUserHasRole(user, RoleEnum.ADMIN);
        if (requestId == null) {
            throw new InvalidIdException();
        }

        boolean deleted = addActivityRequestDAO.delete(requestId);
        if (!deleted) {
            throw new FailedToDeleteCategoryException();
        }
    }

    public AddActivityRequest acceptAddActivityRequest(UUID adminId, UUID requestId) {
        User admin = userDAO.findById(adminId);
        verifyUserNotNull(admin);
        verifyUserHasRole(admin, RoleEnum.ADMIN);
        if (requestId == null) {
            throw new InvalidIdException();
        }
        AddActivityRequest request = addActivityRequestDAO.findById(requestId);
//        if (request == null) {
//            throw new ActivityNotFoundException();
//        }

        return request;
    }

}
