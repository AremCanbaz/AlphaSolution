package com.example.alphasolution.Repository;

import com.example.alphasolution.Model.SubTaskModel;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.alphasolution.Repository.DbManager.connection;

@Repository
public class SubTaskRepository {
    Connection con = DbManager.getConnection();

    public SubTaskModel getSubTaskById(int subTaskId) {
        SubTaskModel subTask = null;

        try {
            String query = "SELECT * FROM subtasks WHERE subtaskid = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, subTaskId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                subTask = new SubTaskModel(
                        rs.getInt("projectid"),
                        rs.getInt("subtaskid"),
                        rs.getString("subtaskname"),
                        rs.getString("subtaskdescription"),
                        rs.getInt("totalhours"),
                        rs.getBoolean("iscompleted")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return subTask;
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

    public String getSubTaskNameById(int subTaskId) {
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
        String query1 = "INSERT INTO subtasks (projectid, subtaskname, SubtaskDescription) VALUES (?,?,?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query1);
            preparedStatement.setInt(1, projectid);
            preparedStatement.setString(2, subtaskdescription);
            preparedStatement.setString(3, subtaskname);
            preparedStatement.executeUpdate();
        }  catch (SQLException sqlException) {
            sqlException.printStackTrace();}
    }
    public void updateSubTask(int subTaskId, String subTaskName, String subTaskDescription) {
        String updateQuery = "UPDATE subtasks SET subtaskname = ?, SubtaskDescription = ? WHERE subtaskid = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, subTaskName);
            preparedStatement.setString(2, subTaskDescription);
            preparedStatement.setInt(3, subTaskId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<SubTaskModel> getSubTasksByProjectId(int projectid) {
        List<SubTaskModel> subTasks = new ArrayList<>();
        try {
            String query = "SELECT * FROM subtasks WHERE projectid = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, projectid);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                SubTaskModel subTask = new SubTaskModel(
                        rs.getInt("projectid"),
                        rs.getInt("subtaskid"),
                        rs.getString("subtaskname"),
                        rs.getString("SubtaskDescription"),
                        rs.getInt("totalhours"),
                        rs.getBoolean("iscompleted")
                );
                subTasks.add(subTask);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subTasks;
    }


}