package com.progforce.scheduler.utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/progforce";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "bora";

    public Connection getConnection() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.err.println("No connection to the database!");
            throw new RuntimeException(e.getMessage());
        }
        return connection;
    }
}
