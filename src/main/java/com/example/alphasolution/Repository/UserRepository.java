package com.example.alphasolution.Repository;

import Model.UserModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class UserRepository {
    @Value("${spring.datasource.url}")
    String databaseURLM;
    @Value("${spring.datasource.username}")
    String userName;
    @Value("${spring.datasource.password}")
    String dBpassword;

    public void addUser(UserModel user){
        String useraddSQL = "insert into users(username, password, email) values(?,?,?)";
        try(Connection connection = DriverManager.getConnection(databaseURLM,userName,dBpassword)){
            PreparedStatement preparedStatement = connection.prepareStatement(useraddSQL);
            PreparedStatement.setString(1,user.getUsername());
            PreparedStatement.setString(2,user.getPassword());
            PreparedStatement.setString(3,user.getEmail());
            PreparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public UserModel findUserByUsernameAndPassword(String username, String password){
        String sql = "select * from users where username = ? and password = ?";
        try(Connection connection = DriverManager.getConnection(databaseURLM,userName,dBpassword)) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            PreparedStatement.setString(1, username);
            PreparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                UserModel user = new UserModel();
                user.setId(resultSet.getLong("user_id"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                return user;
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
