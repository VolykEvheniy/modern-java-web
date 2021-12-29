package com.example.project.model.dao.connection.impl;


import com.example.project.model.dao.connection.ConnectionFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionFactoryImpl implements ConnectionFactory {
    private String driver;
    private String url;
    private String user;
    private String password;
    private static ConnectionFactoryImpl connector;

    private ConnectionFactoryImpl() {
        ResourceBundle resource = ResourceBundle.getBundle("database");
        this.driver = resource.getString("db.driver");
        this.url = resource.getString("db.url");
        this.user = resource.getString("db.user");
        this.password = resource.getString("db.password");

        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static ConnectionFactoryImpl getInstance() {
        if (connector == null) {
            connector = new ConnectionFactoryImpl();
        }
        return connector;
    }
}
