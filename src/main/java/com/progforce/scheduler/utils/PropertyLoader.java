package com.progforce.scheduler.utils;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyLoader {

    private String propFileName = "application.properties";
    private InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
    private Properties property = new Properties();
    private String url;
    private String username;
    private String password;


    public void getPropertyValues() {
            try {
                property.load(inputStream);
                this.url = property.getProperty("db.url");
                this.username = property.getProperty("db.username");
                this.password = property.getProperty("db.password");
            } catch (IOException e) {
                new RuntimeException("Property file " + propFileName + " not found in classpath");
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
    }



    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
