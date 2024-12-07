package com.example.alphasolution.Repository;


import com.example.alphasolution.model.UserModel;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class UserRepository {
    Connection connection = DbManager.getConnection();


    //Metode til at oprette bruger og sende den til databasen
    public void addUser(UserModel user) {
        String useraddSQL = "insert into users(username, passwordd, email) values(?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(useraddSQL);
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Metode til at finde en bruger vha af bruger navn og adgangskode.
    public UserModel findUserByUsernameAndPassword(String username, String password) {
        String sql = "select * from users where username = ? and passwordd = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                UserModel user = new UserModel();
                user.setId(resultSet.getInt("userId"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("passwordd"));
                user.setEmail(resultSet.getString("email"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Metode til at finde brugeren vha af brugerid
    public String findUsernameByUserId(int userId) {
        String sql = "SELECT username FROM users WHERE userid = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, userId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("username");
                }
            }
        } catch (SQLException e) {
            // Log fejlen og genkast en brugerdefineret undtagelse
            throw new RuntimeException("Error fetching username for userId: " + userId, e);
        }
        // Returnér en tom streng eller håndter det på anden måde
        return null; // Eller return ""; hvis tom streng foretrækkes
    }
}
