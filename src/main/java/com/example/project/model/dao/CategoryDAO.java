package com.example.project.model.dao;

import com.example.project.domain.Category;
import com.example.project.model.dao.connection.impl.ConnectionFactoryImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CategoryDAO extends AbstractDAO<UUID, Category> {
    private static CategoryDAO categoryDAO;
    private static final String SELECT_ALL_CATEGORIES = "SELECT * FROM categories WHERE deleted = false";
    private static final String SELECT_CATEGORY_BY_ID = "SELECT * FROM categories WHERE id = ?::uuid AND deleted = false";
    private static final String DELETE_CATEGORY_BY_ID = "UPDATE categories SET deleted = true WHERE id = ?::uuid";
    private static final String INSERT_CATEGORY = "INSERT INTO categories (name) VALUES(?)";
    private static final String UPDATE_CATEGORY = "UPDATE categories SET name = ? WHERE id = ?::uuid";
    private static final String SELECT_CATEGORY_BY_NAME = "SELECT * FROM categories WHERE name = ? AND deleted = false";

    private CategoryDAO() {

    }

    public static CategoryDAO getInstance() {
        if (categoryDAO == null) {
            categoryDAO = new CategoryDAO();
        }
        return categoryDAO;
    }

    @Override
    public List<Category> findAll() {
        List<Category> categories = new ArrayList<>();
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                Statement statement = connection.createStatement()
        ) {
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_CATEGORIES);
            while (resultSet.next()) {
                String id = resultSet.getString(1);
                boolean deleted = resultSet.getBoolean(2);
                String name = resultSet.getString(3);
                categories.add(new Category(UUID.fromString(id), deleted, name));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return categories;
    }

    @Override
    public Category findById(UUID id) {
        Category category = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_CATEGORY_BY_ID)
        ) {
            statement.setString(1, id.toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                boolean deleted = resultSet.getBoolean(2);
                String name = resultSet.getString(3);
                category = new Category(id, deleted, name);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return category;
    }

    @Override
    public boolean delete(UUID id) {
        int rowsAffected = 0;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_CATEGORY_BY_ID)
        ) {
            statement.setString(1, id.toString());
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rowsAffected == 1;
    }

    @Override
    public boolean delete(Category entity) {
        int rowsAffected = 0;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(DELETE_CATEGORY_BY_ID)
        ) {
            statement.setString(1, entity.getId().toString());
            rowsAffected = statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return rowsAffected == 1;
    }

    @Override
    public Category create(Category entity) {
        Category category = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(INSERT_CATEGORY, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, entity.getName());
            int rowAffected = statement.executeUpdate();
            ResultSet resultSet;
            UUID generatedId = null;
            if(rowAffected == 1) {
                resultSet = statement.getGeneratedKeys();
                if (resultSet.next()) {
                    generatedId = (UUID) resultSet.getObject(1);
                }
                if (generatedId != null) {
                    category = entity;
                    category.setId(generatedId);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return category;
    }

    @Override
    public Category update(Category entity) {
        Category category = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(UPDATE_CATEGORY)
        ) {
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getId().toString());
            int rowAffected = statement.executeUpdate();
            if (rowAffected == 1) {
                category = entity;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return category;
    }

    public Category getByName(String name) {
        Category category = null;
        try (
                Connection connection = ConnectionFactoryImpl.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(SELECT_CATEGORY_BY_NAME)
        ) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String id = resultSet.getString(1);
                boolean deleted = resultSet.getBoolean(2);
                category = new Category(UUID.fromString(id), deleted, name);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return category;
    }
}
