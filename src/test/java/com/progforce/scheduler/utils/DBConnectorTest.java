package com.progforce.scheduler.utils;

import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.Assert.assertNotNull;


public class DBConnectorTest {
    private PropertyLoader propertyLoader;
    private static final String URL = "jdbc:mysql://localhost:3306/progforce";
    private static final String USERNAME = "sa";
    private static final String PASSWORD = "bora";

    @Before
    public void setUp() throws Exception {
        propertyLoader = new PropertyLoader();
    }

    @Test
    public void testStaticConnection() throws Exception {
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        assertNotNull(connection);
    }

    @Test
    public void testConnectionWithProperties() throws Exception {
        propertyLoader.getPropertyValues();
        Connection connection = DriverManager.getConnection(propertyLoader.getUrl(), propertyLoader.getUsername(), propertyLoader.getPassword());
        assertNotNull(connection);
    }

    @Test(expected = SQLException.class)
    public void testConnectionWithNoProperties() throws SQLException {
        Connection connection = DriverManager.getConnection(propertyLoader.getUrl(), propertyLoader.getUsername(), propertyLoader.getPassword());
        assertNotNull(connection);
    }

    @Test(timeout = 1000)
    public void testTimeConnection() throws SQLException {
        propertyLoader.getPropertyValues();
        Connection connection = DriverManager.getConnection(propertyLoader.getUrl(), propertyLoader.getUsername(), propertyLoader.getPassword());
        assertNotNull(connection);
    }

}