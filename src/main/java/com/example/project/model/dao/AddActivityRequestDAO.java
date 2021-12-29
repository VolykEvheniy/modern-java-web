package com.example.project.model.dao;

import com.example.project.domain.AddActivityRequest;
import com.example.project.domain.Category;
import com.example.project.domain.User;
import com.example.project.model.dao.connection.impl.ConnectionFactoryImpl;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AddActivityRequestDAO extends AbstractDAO<UUID, AddActivityRequest> {
    private static AddActivityRequestDAO addActivityRequestDAO;
    private static final String SELECT_ALL_REQUESTS = "SELECT * " +
            "FROM add_activity_requests r " +
            "WHERE deleted = false " +
            "AND reviewed_at IS NULL";
    private static final String SELECT_REQUEST_BY_ID = "SELECT * " +
            "FROM add_activity_requests " +
            "WHERE id = ?::uuid AND deleted = false AND reviewed_at IS NULL";
    private static final String DELETE_REQUEST_BY_ID = "UPDATE add_activity_requests " +
            "SET deleted = true " +
            "WHERE id = ?::uuid";
    private static final String INSERT_REQUEST = "INSERT INTO add_activity_requests " +
            "(name, category_id, description, user_id, note) " +
            "VALUES(?, ?::uuid, ?, ?::uuid, ?)";
    private static final String UPDATE_REQUEST = "UPDATE add_activity_requests " +
            "SET name = ?, category_id = ?::uuid, description = ?, " +
            "user_id = ?::uuid, note = ?, accepted = ?, reviewed_at = ? WHERE id = ?::uuid";

    private AddActivityRequestDAO() {

    }

    public static AddActivityRequestDAO getInstance() {
        if (addActivityRequestDAO == null) {
            addActivityRequestDAO = new AddActivityRequestDAO();
        }
        return addActivityRequestDAO;
    }


    @Override
    public List<AddActivityRequest> findAll() {
        List<AddActivityRequest> requests = new ArrayList<>();
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_REQUESTS);
            while (resultSet.next()) {
                String id = resultSet.getString(1);
                boolean deleted = resultSet.getBoolean(2);
                boolean accepted = resultSet.getBoolean(3);
                LocalDateTime reviewed_at = resultSet.getObject(4, LocalDateTime.class);
                String userId = resultSet.getString(5);
                String name = resultSet.getString(6);
                String category_id = resultSet.getString(7);
                String description = resultSet.getString(8);
                String note = resultSet.getString(9);
                User user = new User();
                user.setId(UUID.fromString(userId));
                Category category = new Category();
                category.setId(UUID.fromString(category_id));
                requests.add(new AddActivityRequest(UUID.fromString(id), deleted, accepted, reviewed_at, user, name, category, description, note));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return requests;
    }

    @Override
    public AddActivityRequest findById(UUID id) {
        AddActivityRequest request = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_REQUEST_BY_ID)
        ) {
            statement.setString(1, id.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                boolean deleted = resultSet.getBoolean(2);
                boolean accepted = resultSet.getBoolean(3);
                LocalDateTime reviewed_at = resultSet.getObject(4, LocalDateTime.class);
                String userId = resultSet.getString(5);
                String name = resultSet.getString(6);
                String category_id = resultSet.getString(7);
                String description = resultSet.getString(8);
                String note = resultSet.getString(9);
                User user = new User();
                user.setId(UUID.fromString(userId));
                Category category = new Category();
                category.setId(UUID.fromString(category_id));
                request = new AddActivityRequest(id, deleted, accepted, reviewed_at, user, name, category, description, note);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return request;
    }

    @Override
    public boolean delete(UUID id) {
        int rowsAffected = 0;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_REQUEST_BY_ID)
        ) {
            statement.setString(1, id.toString());
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rowsAffected == 1;
    }

    @Override
    public boolean delete(AddActivityRequest entity) {
        int rowsAffected = 0;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_REQUEST_BY_ID)
        ) {
            statement.setString(1, entity.getId().toString());
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rowsAffected == 1;
    }

    @Override
    public AddActivityRequest create(AddActivityRequest entity) {
        AddActivityRequest request = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(INSERT_REQUEST, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getCategory().getId().toString());
            statement.setString(3, entity.getDescription());
            statement.setString(4, entity.getUser().getId().toString());
            statement.setString(5, entity.getNote());
            int rowAffected = statement.executeUpdate();
            ResultSet resultSet;
            UUID generatedId = null;
            if(rowAffected == 1) {
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    generatedId = (UUID) resultSet.getObject(1);
                }
                if (generatedId != null) {
                    request = entity;
                    request.setId(generatedId);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return request;
    }

    @Override
    public AddActivityRequest update(AddActivityRequest entity) {
        AddActivityRequest request = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_REQUEST)
        ) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getCategory().getId().toString());
            statement.setString(3, entity.getDescription());
            statement.setString(4, entity.getUser().getId().toString());
            statement.setString(5, entity.getNote());
            statement.setBoolean(6, entity.isAccepted());
            statement.setTimestamp(4, Timestamp.valueOf(entity.getReviewedAt()));

            int rowAffected = statement.executeUpdate();
            if (rowAffected == 1) {
                request = entity;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return request;
    }

}