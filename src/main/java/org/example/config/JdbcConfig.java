package org.example.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConfig {
    public static Connection getConnection(){
        Connection connection;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/final-2023-database",
                    "postgres",
                    "57206700"
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
