package com.example.project.model.dao;

import com.example.project.controller.command.receivers.dto.UserReportDto;
import com.example.project.domain.Role;
import com.example.project.domain.User;
import com.example.project.model.dao.connection.impl.ConnectionFactoryImpl;

import java.math.BigInteger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserDAO extends AbstractDAO<UUID, User> {
    private static UserDAO userDAO;

    private final Logger logger = LoggerFactory.getLogger(UserDAO.class);

    private static final String SELECT_ALL_USERS = "SELECT u.*, r.name FROM users u INNER JOIN roles r ON u.role_id = r.id ORDER BY u.login ASC";
    private static final String SELECT_USER_BY_ID = "SELECT u.*, r.name FROM users u INNER JOIN roles r ON u.role_id = r.id WHERE u.id = ?::uuid";
    private static final String DELETE_USER_BY_ID = "UPDATE users SET deleted = true WHERE id = ?::uuid";
    private static final String RESTORE_USER_BY_ID = "UPDATE users SET deleted = false WHERE id = ?::uuid";
    private static final String INSERT_USER = "INSERT INTO users (login, salt, hashed_password, role_id) VALUES(?, ?, ?, ?::uuid)";
    private static final String UPDATE_USER = "UPDATE users SET login = ?, salt = ?, hashed_password = ?, role_id = ?::uuid WHERE id = ?)";
    private static final String SELECT_USER_BY_LOGIN = "SELECT u.*, r.name FROM users u INNER JOIN roles r ON u.role_id = r.id WHERE u.login = ? AND u.deleted = false";
    private static final String SELECT_REPORT_DATA =
            "SELECT DISTINCT u.id, u.login, COUNT(u2a.*) AS activities_count, SUM(u2a.time_spent) AS total_time_spent " +
            "FROM users u " +
            "LEFT JOIN users2activities u2a ON u2a.user_id = u.id " +
            "WHERE u.deleted = false " +
            "GROUP BY u.id " +
            "ORDER BY u.login ASC";

    private UserDAO() {

    }

    public static UserDAO getInstance() {
        if (userDAO == null) {
            userDAO = new UserDAO();
        }
        return userDAO;
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_USERS);
            while (resultSet.next()) {
                String id = resultSet.getString(1);
                boolean deleted = resultSet.getBoolean(2);
                String login = resultSet.getString(3);
                String salt = resultSet.getString(4);
                String hashedPassword = resultSet.getString(5);
                String roleId = resultSet.getString(6);
                String roleName = resultSet.getString(7);
                Role role = new Role();
                role.setId(UUID.fromString(roleId));
                role.setName(roleName);
                users.add(new User(UUID.fromString(id), deleted, login, salt, hashedPassword, role));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        logger.info("Find all users");
        return users;
    }

    @Override
    public User findById(UUID id) {
        User user = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_ID)
        ) {
            statement.setString(1, id.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                boolean deleted = resultSet.getBoolean(2);
                String login = resultSet.getString(3);
                String salt = resultSet.getString(4);
                String hashedPassword = resultSet.getString(5);
                String roleId = resultSet.getString(6);
                String roleName = resultSet.getString(7);
                Role role = new Role();
                role.setId(UUID.fromString(roleId));
                role.setName(roleName);
                user = new User(id, deleted, login, salt, hashedPassword, role);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        logger.info("Find user by id: " + id);
        return user;
    }

    @Override
    public boolean delete(UUID id) {
        int rowsAffected = 0;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_USER_BY_ID)
        ) {
            statement.setString(1, id.toString());
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        logger.info("Deleted user with id: " + id);
        return rowsAffected == 1;
    }

    @Override
    public boolean delete(User entity) {
        int rowsAffected = 0;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_USER_BY_ID)
        ) {
            statement.setString(1, entity.getId().toString());
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        logger.info("Deleted user with id: " + entity.getId());
        return rowsAffected == 1;
    }

    @Override
    public User create(User entity) {
        User user = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, entity.getLogin());
            statement.setString(2, entity.getSalt());
            statement.setString(3, entity.getHashedPassword());
            statement.setString(4, entity.getRole().getId().toString());
            int rowAffected = statement.executeUpdate();
            ResultSet resultSet;
            UUID generatedId = null;
            if(rowAffected == 1) {
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    generatedId = (UUID) resultSet.getObject(1);
                }
                if (generatedId != null) {
                    user = entity;
                    user.setId(generatedId);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        logger.info("Created user with id: " + entity.getId());
        return user;
    }

    @Override
    public User update(User entity) {
        User user = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_USER)
        ) {
            statement.setString(1, entity.getLogin());
            statement.setString(2, entity.getSalt());
            statement.setString(3, entity.getHashedPassword());
            statement.setString(4, entity.getRole().getId().toString());
            statement.setString(5, entity.getId().toString());
            int rowAffected = statement.executeUpdate();
            if (rowAffected == 1) {
                user = entity;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        logger.info("Updated user with id: " + entity.getId());
        return user;
    }

    public User getByLogin(String login) {
        User user = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_USER_BY_LOGIN)
        ) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String id = resultSet.getString(1);
                boolean deleted = resultSet.getBoolean(2);
                String salt = resultSet.getString(4);
                String hashedPassword = resultSet.getString(5);
                String roleId = resultSet.getString(6);
                String roleName = resultSet.getString(7);
                Role role = new Role();
                role.setId(UUID.fromString(roleId));
                role.setName(roleName);
                user = new User(UUID.fromString(id), deleted, login, salt, hashedPassword, role);
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        logger.info("Get user by login: " + login);
        return user;
    }

    public boolean restore(UUID id) {
        int rowsAffected = 0;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(RESTORE_USER_BY_ID)
        ) {
            statement.setString(1, id.toString());
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        logger.info("Restored user with id: " + id);
        return rowsAffected == 1;
    }

    public List<UserReportDto> selectUsersReportData() {
        List<UserReportDto> userReports = new ArrayList<>();
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(SELECT_REPORT_DATA);
            while (resultSet.next()) {
                String id = resultSet.getString(1);
                String login = resultSet.getString(2);
                Integer activitiesAmount = resultSet.getInt(3);
                Integer totalTimeSpent = resultSet.getInt(4);
                userReports.add(new UserReportDto(UUID.fromString(id), login, activitiesAmount, BigInteger.valueOf(totalTimeSpent)));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
        logger.info("Selected Repost data");
        return userReports;
    }
}
