package com.livescore.livescore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=HandballLeague;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "Magnus";
    private static final String PASSWORD = "Jpc77rfc";

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Failed to load SQL Server JDBC driver", e);
        }
    }

    public static Connection connect() {
    try {
        System.out.println("Attempting to connect to database...");
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("Connection successful!");
        return connection;
    } catch (SQLException e) {
        System.out.println("Connection failed: " + e.getMessage());
        e.printStackTrace();
        return null;
    }
}
}