package com.progforce.scheduler.utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConnector {
    PropertyLoader propertyLoader = new PropertyLoader();

    public DBConnector() {
        propertyLoader.getPropertyValues();
    }

    public Connection getConnection() {
        Connection connection;
        try {
            connection = DriverManager.getConnection(propertyLoader.getUrl(), propertyLoader.getUsername(), propertyLoader.getPassword());
        } catch (SQLException e) {
            System.err.println("No connection to the database!");
            throw new RuntimeException(e.getMessage());
        }
        return connection;
    }
}




