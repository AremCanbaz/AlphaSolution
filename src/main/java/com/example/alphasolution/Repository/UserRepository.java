package com.example.alphasolution.Repository;


import com.example.alphasolution.Model.UserModel;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class UserRepository {
 Connection connection = DbManager.getConnection();

    public void addUser(UserModel user){
        String useraddSQL = "insert into users(username, passwordd, email) values(?,?,?)";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(useraddSQL);
            preparedStatement.setString(1,user.getUsername());
            preparedStatement.setString(2,user.getPassword());
            preparedStatement.setString(3,user.getEmail());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public UserModel findUserByUsernameAndPassword(String username, String password){
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
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
