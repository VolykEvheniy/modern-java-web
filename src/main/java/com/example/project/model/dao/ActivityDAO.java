package com.example.project.model.dao;

import com.example.project.domain.Activity;
import com.example.project.domain.Category;
import com.example.project.domain.enums.ActivitySortCriteria;
import com.example.project.domain.enums.SortOrder;
import com.example.project.model.dao.connection.impl.ConnectionFactoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ActivityDAO extends AbstractDAO<UUID, Activity> {
    private static ActivityDAO activityDAO;

    private final Logger logger = LoggerFactory.getLogger(ActivityDAO.class);

    private static final String SELECT_ALL_ACTIVITIES = "SELECT *, c.name AS category_name " +
            "FROM activities a " +
            "INNER JOIN categories c ON a.category_id = c.id " +
            "WHERE a.deleted = false";
    private static final String SELECT_ACTIVITIES_SORTED_BY_NAME = "SELECT a.*, c.name AS category_name " +
            "FROM activities a " +
            "INNER JOIN categories c ON a.category_id = c.id " +
            "WHERE a.deleted = false " +
            "ORDER BY name";
    private static final String SELECT_ACTIVITIES_SORTED_BY_CATEGORY = "SELECT a.*, c.name AS category_name " +
            "FROM activities a " +
            "INNER JOIN categories c ON a.category_id = c.id " +
            "WHERE a.deleted = false " +
            "ORDER BY c.name";
    private static final String SELECT_ACTIVITIES_SORTED_BY_USERS_COUNT = "SELECT a.*, c.name AS category_name, COUNT(u2a.*) as users_count " +
            "FROM activities a " +
            "LEFT JOIN users2activities u2a ON u2a.activity_id = a.id " +
            "INNER JOIN categories c ON a.category_id = c.id " +
            "WHERE a.deleted = false " +
            "GROUP BY a.id, c.name " +
            "ORDER BY users_count";
    private static final String SELECT_ACTIVITY_BY_ID = "SELECT * FROM activities WHERE id = ?::uuid AND deleted = false";
    private static final String DELETE_ACTIVITY_BY_ID = "UPDATE activities SET deleted = true WHERE id = ?::uuid";
    private static final String INSERT_ACTIVITY = "INSERT INTO activities (name, category_id, description, created_at) VALUES(?, ?::uuid, ?, ?)";
    private static final String UPDATE_ACTIVITY = "UPDATE activities SET name = ?, category_id = ?::uuid, description = ?, created_at = ? WHERE id = ?::uuid)";

    private ActivityDAO() {

    }

    public static ActivityDAO getInstance() {
        if (activityDAO == null) {
            activityDAO = new ActivityDAO();
        }
        return activityDAO;
    }

    @Override
    public List<Activity> findAll() {
        List<Activity> activities = new ArrayList<>();
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_ACTIVITIES);
            while (resultSet.next()) {
                String id = resultSet.getString(1);
                boolean deleted = resultSet.getBoolean(2);
                String name = resultSet.getString(3);
                String category_id = resultSet.getString(4);
                String description = resultSet.getString(5);
                LocalDateTime created_at = resultSet.getObject(6, LocalDateTime.class);
                String categoryName = resultSet.getString(7);
                Category category = new Category();
                category.setId(UUID.fromString(category_id));
                category.setName(categoryName);
                activities.add(new Activity(UUID.fromString(id), deleted, name, category, description, created_at));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return activities;
    }

    public List<Activity> getSortedActivities(ActivitySortCriteria sortCriteria, SortOrder sortOrder) {
        String query;

        switch (sortCriteria) {
            case NAME:
                query = SELECT_ACTIVITIES_SORTED_BY_NAME;
                break;
            case CATEGORY:
                query = SELECT_ACTIVITIES_SORTED_BY_CATEGORY;
                break;
            case USERS_COUNT:
                query = SELECT_ACTIVITIES_SORTED_BY_USERS_COUNT;
                break;
            default:
                query = SELECT_ALL_ACTIVITIES;
                break;
        }
        query += " " + sortOrder.toString();


        List<Activity> activities = new ArrayList<>();
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                String id = resultSet.getString(1);
                boolean deleted = resultSet.getBoolean(2);
                String name = resultSet.getString(3);
                String category_id = resultSet.getString(4);
                String description = resultSet.getString(5);
                LocalDateTime created_at = resultSet.getObject(6, LocalDateTime.class);
                String categoryName = resultSet.getString(7);
                Category category = new Category();
                category.setId(UUID.fromString(category_id));
                category.setName(categoryName);
                activities.add(new Activity(UUID.fromString(id), deleted, name, category, description, created_at));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return activities;
    }

    @Override
    public Activity findById(UUID id) {
        Activity activity = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_ACTIVITY_BY_ID)
        ) {
            statement.setString(1, id.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                boolean deleted = resultSet.getBoolean(2);
                String name = resultSet.getString(3);
                String category_id = resultSet.getString(4);
                String description = resultSet.getString(5);
                LocalDateTime created_at = resultSet.getObject(6, LocalDateTime.class);
                Category category = new Category();
                category.setId(UUID.fromString(category_id));
                activity = new Activity(id, deleted, name, category, description, created_at);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return activity;
    }

    @Override
    public boolean delete(UUID id) {
        int rowsAffected = 0;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_ACTIVITY_BY_ID)
        ) {
            statement.setString(1, id.toString());
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return rowsAffected == 1;
    }

    @Override
    public boolean delete(Activity entity) {
        int rowsAffected = 0;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_ACTIVITY_BY_ID)
        ) {
            statement.setString(1, entity.getId().toString());
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return rowsAffected == 1;
    }

    @Override
    public Activity create(Activity entity) {
        Activity activity = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(INSERT_ACTIVITY, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getCategory().getId().toString());
            statement.setString(3, entity.getDescription());
            statement.setTimestamp(4, Timestamp.valueOf(entity.getCreatedAt()));
            int rowAffected = statement.executeUpdate();
            ResultSet resultSet;
            UUID generatedId = null;
            if(rowAffected == 1) {
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    generatedId = (UUID) resultSet.getObject(1);
                }
                if (generatedId != null) {
                    activity = entity;
                    activity.setId(generatedId);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return activity;
    }

    @Override
    public Activity update(Activity entity) {
        Activity activity = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_ACTIVITY)
        ) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getCategory().getId().toString());
            statement.setString(3, entity.getDescription());
            statement.setTimestamp(4, Timestamp.valueOf(entity.getCreatedAt()));
            statement.setString(5, entity.getId().toString());
            int rowAffected = statement.executeUpdate();
            if (rowAffected == 1) {
                activity = entity;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        return activity;
    }

}
