package com.example.alphasolution.Repository;

import com.example.alphasolution.Model.SubTaskModel;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

import static com.example.alphasolution.Repository.DbManager.connection;

@Repository
public class SubTaskRepository {
    Connection con = DbManager.getConnection();

    public ArrayList<SubTaskModel> getSubTaskesById(
    int projectid) {
        ArrayList<SubTaskModel> subTasks = new ArrayList<>();

        try {
            String query = "select * from subtasks where projectid = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, projectid);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                SubTaskModel subTask = new SubTaskModel(
                        rs.getInt("projectid"),
                        rs.getInt("subtaskid"),
                        rs.getString("subtaskdescription"),
                        rs.getString("subtaskname"),
                        rs.getInt("totalhours"),
                        rs.getBoolean("iscompleted")
                );
                subTasks.add(subTask);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return subTasks;
    }

    public void deleteSubTask(int subTaskId) {
        String deleteQuery = "DELETE FROM subtasks WHERE subtaskid = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, subTaskId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getTaskNameById(int subTaskId) {
        String query = "SELECT subtaskname FROM subtasks WHERE subtaskid = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, subTaskId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("subtaskname");
                }

            }
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }
    public void createSubTask(int projectid, String subtaskdescription, String subtaskname) {
        String query1 = "INSERT INTO subtasks (projectid, subtaskname, subtaskdescription) VALUES (?,?,?)";
        try {
        PreparedStatement preparedStatement = con.prepareStatement(query1);
        preparedStatement.setInt(1, projectid);
        preparedStatement.setString(2, subtaskdescription);
        preparedStatement.setString(3, subtaskname);
        preparedStatement.executeUpdate();
    }  catch (SQLException sqlException) {
        sqlException.printStackTrace();}
    }
}