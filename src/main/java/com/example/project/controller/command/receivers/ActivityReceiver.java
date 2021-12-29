package com.example.project.controller.command.receivers;

import com.example.project.controller.command.receivers.dto.UserActivityDto;
import com.example.project.controller.command.receivers.dto.UserReportDto;
import com.example.project.controller.exception.*;
import com.example.project.domain.*;
import com.example.project.domain.enums.ActivityFilterCriteria;
import com.example.project.domain.enums.ActivitySortCriteria;
import com.example.project.domain.enums.RoleEnum;
import com.example.project.domain.enums.SortOrder;
import com.example.project.model.dao.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.project.controller.util.ValidationUtils.verifyUserHasRole;
import static com.example.project.controller.util.ValidationUtils.verifyUserNotNull;

public class ActivityReceiver {
    private static ActivityReceiver activityReceiver;
    private ActivityDAO activityDAO;
    private User2ActivityDAO user2ActivityDAO;
    private CategoryDAO categoryDAO;
    private UserDAO userDAO;

    private ActivityReceiver() {
        DaoFactory factory = DaoFactory.getInstance();
        activityDAO = (ActivityDAO) factory.getDAO(DaoClass.ACTIVITY_DAO);
        user2ActivityDAO = (User2ActivityDAO) factory.getDAO(DaoClass.USER2ACTIVITY_DAO);
        categoryDAO = (CategoryDAO) factory.getDAO(DaoClass.CATEGORY_DAO);
        userDAO = (UserDAO) factory.getDAO(DaoClass.USER_DAO);
    }

    public static ActivityReceiver getInstance() {
        if (activityReceiver == null) {
            activityReceiver = new ActivityReceiver();
        }
        return activityReceiver;
    }

    public List<Activity> getAllActivitiesForAdmin(
            UUID userId,
            ActivitySortCriteria sortCriteria,
            SortOrder sortOrder,
            ActivityFilterCriteria filterCriteria,
            String filterValue
    ) {
        User user = userDAO.findById(userId);
        verifyUserNotNull(user);
        verifyUserHasRole(user, RoleEnum.ADMIN);

        ActivitySortCriteria sortCriteriaOrDefault = sortCriteria == null ? ActivitySortCriteria.NAME : sortCriteria;
        SortOrder sortOrderOrDefault = sortOrder == null ? SortOrder.ASC : sortOrder;

        List<Activity> sortedActivities = activityDAO.getSortedActivities(sortCriteriaOrDefault, sortOrderOrDefault);

        if (filterCriteria != null && filterValue != null) {
            if (filterCriteria.equals(ActivityFilterCriteria.CATEGORY)) {
                sortedActivities = sortedActivities.stream()
                        .filter(activity -> activity.getCategory().getName().equals(filterValue))
                        .collect(Collectors.toList());
            }
        }

        return sortedActivities;
    }

    public Activity createActivity(UUID userId, String activityName, UUID categoryId, String description) {
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
        Activity activity = new Activity();
        activity.setName(activityName);
        activity.setCategory(category);
        activity.setDescription(description);
        activity.setCreatedAt(LocalDateTime.now());

        Activity createdActivity = activityDAO.create(activity);
        createdActivity.setCategory(category);

        return createdActivity;
    }

    public void deleteActivity(UUID userId, UUID activityId) {
        User user = userDAO.findById(userId);
        verifyUserNotNull(user);
        verifyUserHasRole(user, RoleEnum.ADMIN);
        if (activityId == null) {
            throw new InvalidIdException();
        }

        boolean deleted = activityDAO.delete(activityId);
        if (!deleted) {
            throw new FailedToDeleteCategoryException();
        }
    }

    public User2Activity pinActivity(UUID adminId, UUID userId, UUID activityId) {
        User admin = userDAO.findById(adminId);
        User user = userDAO.findById(userId);
        verifyUserNotNull(admin);
        verifyUserNotNull(user);
        verifyUserHasRole(admin, RoleEnum.ADMIN);
        verifyUserHasRole(user, RoleEnum.USER);
        if (activityId == null) {
            throw new InvalidIdException();
        }
        Activity activity = activityDAO.findById(activityId);
        if (activity == null) {
            throw new ActivityNotFoundException();
        }

        User2Activity user2Activity = new User2Activity();
        user2Activity.setUser(user);
        user2Activity.setActivity(activity);
        user2Activity.setAssigned_at(LocalDateTime.now());
        user2Activity.setTimeSpent(BigInteger.ZERO);

        User2Activity createdUser2Activity = user2ActivityDAO.create(user2Activity);
        createdUser2Activity.setUser(user);
        createdUser2Activity.setActivity(activity);

        return createdUser2Activity;
    }

    public void unpinActivity(UUID adminId, UUID userId, UUID activityId) {
        User admin = userDAO.findById(adminId);
        User user = userDAO.findById(userId);
        verifyUserNotNull(admin);
        verifyUserNotNull(user);
        verifyUserHasRole(admin, RoleEnum.ADMIN);
        verifyUserHasRole(user, RoleEnum.USER);
        if (activityId == null) {
            throw new InvalidIdException();
        }
        Activity activity = activityDAO.findById(activityId);
        if (activity == null) {
            throw new ActivityNotFoundException();
        }

        User2Activity user2Activity = user2ActivityDAO.findByActivityIdAndUserId(activityId, userId);

        boolean unpinned = user2ActivityDAO.delete(user2Activity.getId());
        if (!unpinned) {
            throw new FailedToUnpinActivityException();
        }
    }

    public List<User2Activity> getUser2ActivitiesForAdmin(UUID userId, UUID activityId) {
        User user = userDAO.findById(userId);
        verifyUserNotNull(user);
        verifyUserHasRole(user, RoleEnum.ADMIN);
        if (activityId == null) {
            throw new InvalidPinActivityRequestDataException();
        }

        return user2ActivityDAO.findAllByActivityId(activityId);
    }

    public List<UserActivityDto> getUser2ActivitiesForUser(UUID userId) {
        User user = userDAO.findById(userId);
        verifyUserNotNull(user);
        verifyUserHasRole(user, RoleEnum.USER);

        return user2ActivityDAO.findAllByUserId(userId);
    }

    public void setTimeSpent(UUID userId, UUID userToActivityId, BigInteger timeSpent) {
        User user = userDAO.findById(userId);
        verifyUserNotNull(user);
        verifyUserHasRole(user, RoleEnum.USER);
        if (userToActivityId == null) {
            throw new InvalidIdException();
        }
        BigInteger timeSpentSeconds = timeSpent.multiply(BigInteger.valueOf(60));
        User2Activity user2Activity = user2ActivityDAO.findById(userToActivityId);
        user2Activity.setTimeSpent(timeSpentSeconds);

        user2ActivityDAO.update(user2Activity);
    }

}
