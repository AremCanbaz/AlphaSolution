package com.example.alphasolution.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Denne metode er DBManager, som sørger for at vi kun connecte til databasen en gang, istedet for flere connections.
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

        // Miljøvariabellerne tilknyttet editoren
        String url = System.getenv("PROD_URL");
        String user = System.getenv("PROD_USERNAME");
        String password = System.getenv("PROD_PASSWORD");

        // henter driveren til at connecte til databasen fra vore POM fil,
        // hvis den fejler siger den Driveren ikke kunne findes, eller en anden fejl som kunne tyde på miljøvariablerne har en fejl
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Database connection established.");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error establishing database connection: " + e.getMessage());
        }
        // Returnere forbindelsen til databasen
        return connection;
    }
}

