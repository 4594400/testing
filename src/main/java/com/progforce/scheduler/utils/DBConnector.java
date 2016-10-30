package com.progforce.scheduler.utils;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DBConnector {

    FileInputStream fis;
    Properties property = new Properties();

    public Connection getConnection() {
        Connection connection;
        try {
            fis = new FileInputStream("src/main/resources/application.properties");
            property.load(fis);
            String url = property.getProperty("db.url");
            String username = property.getProperty("db.username");
            String password = property.getProperty("db.password");
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            System.err.println("No connection to the database!");
            throw new RuntimeException(e.getMessage());
        } catch (FileNotFoundException e) {
            System.err.println("File application.properties not found!");
            throw new RuntimeException(e.getMessage());
        } catch (IOException e) {
            System.err.println("No connection to the database!");
            throw new RuntimeException(e.getMessage());
        }
        return connection;
    }
}




