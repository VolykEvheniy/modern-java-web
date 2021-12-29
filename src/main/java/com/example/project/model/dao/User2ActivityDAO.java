package com.example.project.model.dao;

import com.example.project.controller.command.receivers.dto.UserActivityDto;
import com.example.project.domain.Activity;
import com.example.project.domain.User;
import com.example.project.domain.User2Activity;
import com.example.project.model.dao.connection.impl.ConnectionFactoryImpl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User2ActivityDAO extends AbstractDAO<UUID, User2Activity> {
    private static User2ActivityDAO user2ActivityDAO;
    private static final String SELECT_ALL_USER2ACTIVITIES = "SELECT * FROM users2activities WHERE deleted = false";
    private static final String SELECT_USER2ACTIVITIES_BY_USER_ID = "SELECT u2a.id, a.name, a.description, u2a.time_spent, u2a.completed " +
            "FROM users2activities u2a " +
            "INNER JOIN activities a ON u2a.activity_id = a.id " +
            "WHERE u2a.user_id = ?::uuid " +
            "AND u2a.deleted = false";
    private static final String SELECT_USER2ACTIVITY_BY_ID = "SELECT * " +
            "FROM users2activities " +
            "WHERE id = ?::uuid " +
            "AND deleted = false";
    private static final String SELECT_ALL_USER2ACTIVITY_BY_ACTIVITY_ID = "SELECT * " +
            "FROM users2activities " +
            "WHERE activity_id = ?::uuid " +
            "AND deleted = false " +
            "ORDER BY assigned_at DESC";
    private static final String SELECT_USER2ACTIVITY_BY_ACTIVITY_AND_USER = "SELECT * " +
            "FROM users2activities " +
            "WHERE activity_id = ?::uuid " +
            "AND user_id = ?::uuid " +
            "AND deleted = false";
    private static final String DELETE_USER2ACTIVITY_BY_ID = "UPDATE users2activities SET deleted = true WHERE id = ?::uuid";
    private static final String INSERT_USER2ACTIVITY = "INSERT INTO users2activities (user_id, activity_id, time_spent, completed, assigned_at) VALUES(?::uuid, ?::uuid, ?, ?, ?)";
    private static final String UPDATE_USER2ACTIVITY = "UPDATE users2activities SET user_id = ?::uuid, activity_id = ?::uuid, time_spent = ?, completed = ?, assigned_at = ? WHERE id = ?::uuid";

    private User2ActivityDAO() {

    }

    public static User2ActivityDAO getInstance() {
        if (user2ActivityDAO == null) {
            user2ActivityDAO = new User2ActivityDAO();
        }
        return user2ActivityDAO;
    }

    @Override
    public List<User2Activity> findAll() {
        List<User2Activity> user2Activities = new ArrayList<>();
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_USER2ACTIVITIES);
            while (resultSet.next()) {
                UUID id = resultSet.getObject(1, UUID.class);
                boolean deleted = resultSet.getBoolean(2);
                UUID user_id = resultSet.getObject(3, UUID.class);
                UUID activity_id = resultSet.getObject(4, UUID.class);
                BigInteger time_spent = resultSet.getObject(5, BigInteger.class);
                boolean completed = resultSet.getBoolean(6);
                LocalDateTime assigned_at = resultSet.getObject(7, LocalDateTime.class);

                User user = new User();
                user.setId(user_id);

                Activity activity = new Activity();
                activity.setId(activity_id);

                user2Activities.add(new User2Activity(id, deleted, user, activity, time_spent, completed, assigned_at));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user2Activities;
    }

    public List<User2Activity> findAllByActivityId(UUID activityId) {
        List<User2Activity> user2Activities = new ArrayList<>();
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_ALL_USER2ACTIVITY_BY_ACTIVITY_ID)
        ) {
            statement.setString(1, activityId.toString());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UUID id = resultSet.getObject(1, UUID.class);
                boolean deleted = resultSet.getBoolean(2);
                UUID user_id = resultSet.getObject(3, UUID.class);
                UUID activity_id = resultSet.getObject(4, UUID.class);
                BigInteger time_spent = resultSet.getObject(5, BigInteger.class);
                boolean completed = resultSet.getBoolean(6);
                LocalDateTime assigned_at = resultSet.getObject(7, LocalDateTime.class);

                User user = new User();
                user.setId(user_id);

                Activity activity = new Activity();
                activity.setId(activity_id);

                user2Activities.add(new User2Activity(id, deleted, user, activity, time_spent, completed, assigned_at));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user2Activities;
    }

    @Override
    public User2Activity findById(UUID id) {
        User2Activity user2Activity = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_USER2ACTIVITY_BY_ID)
        ) {
            statement.setString(1, id.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                boolean deleted = resultSet.getBoolean(2);
                UUID user_id = resultSet.getObject(3, UUID.class);
                UUID activity_id = resultSet.getObject(4, UUID.class);
                BigInteger time_spent = resultSet.getObject(5, BigInteger.class);
                boolean completed = resultSet.getBoolean(6);
                LocalDateTime assigned_at = resultSet.getObject(7, LocalDateTime.class);

                User user = new User();
                user.setId(user_id);

                Activity activity = new Activity();
                activity.setId(activity_id);

                user2Activity = new User2Activity(id, deleted, user, activity, time_spent, completed, assigned_at);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user2Activity;
    }

    public User2Activity findByActivityIdAndUserId(UUID activityId, UUID userId) {
        User2Activity user2Activity = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_USER2ACTIVITY_BY_ACTIVITY_AND_USER)
        ) {
            statement.setString(1, activityId.toString());
            statement.setString(2, userId.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                UUID id = resultSet.getObject(1, UUID.class);
                boolean deleted = resultSet.getBoolean(2);
                UUID user_id = resultSet.getObject(3, UUID.class);
                UUID activity_id = resultSet.getObject(4, UUID.class);
                BigInteger time_spent = resultSet.getObject(5, BigInteger.class);
                boolean completed = resultSet.getBoolean(6);
                LocalDateTime assigned_at = resultSet.getObject(7, LocalDateTime.class);

                User user = new User();
                user.setId(user_id);

                Activity activity = new Activity();
                activity.setId(activity_id);

                user2Activity = new User2Activity(id, deleted, user, activity, time_spent, completed, assigned_at);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user2Activity;
    }

    public List<UserActivityDto> findAllByUserId(UUID userId) {
        List<UserActivityDto> userActivities = new ArrayList<>();
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_USER2ACTIVITIES_BY_USER_ID)
        ) {
            statement.setString(1, userId.toString());
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UUID id = resultSet.getObject(1, UUID.class);
                String name = resultSet.getString(2);
                String description = resultSet.getString(3);
                BigInteger time_spent = resultSet.getObject(4, BigInteger.class);
                boolean completed = resultSet.getBoolean(5);

                userActivities.add(new UserActivityDto(id, name, description, time_spent, completed));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userActivities;
    }

    @Override
    public boolean delete(UUID id) {
        int rowsAffected = 0;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_USER2ACTIVITY_BY_ID)
        ) {
            statement.setString(1, id.toString());
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rowsAffected == 1;
    }

    @Override
    public boolean delete(User2Activity entity) {
        int rowsAffected = 0;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_USER2ACTIVITY_BY_ID)
        ) {
            statement.setString(1, entity.getId().toString());
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rowsAffected == 1;
    }

    @Override
    public User2Activity create(User2Activity entity) {
        User2Activity user2Activity = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(INSERT_USER2ACTIVITY, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, entity.getUser().getId().toString());
            statement.setString(2, entity.getActivity().getId().toString());
            statement.setBigDecimal(3, new BigDecimal(entity.getTimeSpent()));
            statement.setBoolean(4, entity.isCompleted());
            statement.setTimestamp(5, Timestamp.valueOf(entity.getAssigned_at()));
            int rowAffected = statement.executeUpdate();
            ResultSet resultSet;
            UUID generatedId = null;
            if(rowAffected == 1) {
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    generatedId = (UUID) resultSet.getObject(1);
                }
                if (generatedId != null) {
                    user2Activity = entity;
                    user2Activity.setId(generatedId);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user2Activity;
    }

    @Override
    public User2Activity update(User2Activity entity) {
        User2Activity user2Activity = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_USER2ACTIVITY)
        ) {
            statement.setString(1, entity.getUser().getId().toString());
            statement.setString(2, entity.getActivity().getId().toString());
            statement.setBigDecimal(3, new BigDecimal(entity.getTimeSpent()));
            statement.setBoolean(4, entity.isCompleted());
            statement.setTimestamp(5, Timestamp.valueOf(entity.getAssigned_at()));
            statement.setString(6, entity.getId().toString());
            int rowAffected = statement.executeUpdate();
            if (rowAffected == 1) {
                user2Activity = entity;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user2Activity;
    }

}
