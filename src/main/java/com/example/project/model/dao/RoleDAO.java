package com.example.project.model.dao;

import com.example.project.domain.Role;
import com.example.project.model.dao.connection.impl.ConnectionFactoryImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class RoleDAO extends AbstractDAO<UUID, Role> {
    private static RoleDAO roleDAO;
    private static final String SELECT_ROLE_BY_ID = "SELECT * FROM roles WHERE id = ?";
    private static final String SELECT_ROLE_BY_NAME = "SELECT * FROM roles WHERE name = ?";

    private RoleDAO() {

    }

    public static RoleDAO getInstance() {
        if (roleDAO == null) {
            roleDAO = new RoleDAO();
        }
        return roleDAO;
    }

    @Override
    public List<Role> findAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Role findById(UUID id) {
        Role role = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_ROLE_BY_ID)
        ) {
            statement.setString(1, id.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                boolean deleted = resultSet.getBoolean(2);
                String name = resultSet.getString(3);
                role = new Role(id, deleted, name);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return role;
    }

    @Override
    public boolean delete(UUID id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(Role entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Role create(Role entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Role update(Role entity) {
        throw new UnsupportedOperationException();
    }

    public Role getByName(String name) {
        Role role = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_ROLE_BY_NAME)
        ) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String id = resultSet.getString(1);
                boolean deleted = resultSet.getBoolean(2);
                role = new Role(UUID.fromString(id), deleted, name);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return role;
    }
}
