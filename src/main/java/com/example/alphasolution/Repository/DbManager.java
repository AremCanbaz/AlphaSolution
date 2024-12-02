package com.example.alphasolution.Repository;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbManager {
    static Connection connection = null;

    public static Connection getConnection() {


        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        String url = "jdbc:mysql://localhost:3306/alphasolution";
        String user = "root";
        String password = "";

        if (connection != null)
            return connection;

        try (InputStream input = new ClassPathResource("application.properties").getInputStream()) {
            Properties properties = new Properties();
            properties.load(input);
            url = properties.getProperty("url");
            user = properties.getProperty("user");
            password = properties.getProperty("password");

        } catch (IOException error) {
            error.printStackTrace();
        }

        try {
            connection = DriverManager.getConnection(url, user, password);

        } catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }
}