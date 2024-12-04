package com.example.alphasolution.Repository;

import com.example.alphasolution.Model.SubTaskModel;
import com.example.alphasolution.Model.TaskModel;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.example.alphasolution.Repository.DbManager.connection;

@Repository
public class TaskRepository {
    Connection con = DbManager.getConnection();

    public ArrayList<TaskModel> getTaskesById(
            int subTaskId) {
        ArrayList<TaskModel> tasks = new ArrayList<>();

        try {
            String query = "select * from tasks where subtaskid = ?";
            PreparedStatement stmt = con.prepareStatement(query);
            stmt.setInt(1, subTaskId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TaskModel task = new TaskModel(
                        rs.getInt("taskid"),
                        rs.getInt("subtaskid"),
                        rs.getString("taskname"),
                        rs.getString("description"),
                        rs.getInt("hoursspent"),
                        rs.getBoolean("iscompleted")
                );
               tasks.add(task);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }
    public void deleteTask(int taskId) {
        String deleteQuery = "DELETE FROM tasks WHERE taskid = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, taskId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
}
    public String getTaskNameById(int taskId) {
        String query = "SELECT taskname FROM tasks WHERE taskid = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, taskId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("taskname");
                }

            }
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }
}