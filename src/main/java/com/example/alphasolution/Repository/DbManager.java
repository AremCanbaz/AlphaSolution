package com.example.alphasolution.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DbManager {
    static Connection connection = null;

    public static Connection getConnection() {
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    return connection;
                }
            } catch (SQLException e) {
                System.out.println("Error validating connection: " + e.getMessage());
            }
        }

        String url = System.getenv("PROD_URL"); // Miljøvariabel
        String user = System.getenv("PROD_USERNAME");
        String password = System.getenv("PROD_PASSWORD");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Database connection established.");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error establishing database connection: " + e.getMessage());
        }
        return connection;
    }
}

