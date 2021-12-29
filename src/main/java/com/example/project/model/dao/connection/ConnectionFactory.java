package com.example.project.model.dao.connection;

import java.sql.Connection;

public interface ConnectionFactory {
    Connection getConnection();
}
