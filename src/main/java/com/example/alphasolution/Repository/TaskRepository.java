package com.example.alphasolution.Repository;

import com.example.alphasolution.Model.SubTaskModel;
import com.example.alphasolution.Model.TaskModel;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
    public void createTask(int subtaskid, String taskdescription, String taskname, int time) {
        String query1 = "INSERT INTO tasks (subtaskid, taskname, description, hoursspent) VALUES (?,?,?,?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query1);
            preparedStatement.setInt(1, subtaskid);
            preparedStatement.setString(2, taskname);
            preparedStatement.setString(3, taskdescription);
            preparedStatement.setInt(4, time);
            preparedStatement.executeUpdate();
        }  catch (SQLException sqlException) {
            sqlException.printStackTrace();}
    }
    public void editTask(int taskId, String taskname, String description, int hoursspent, boolean isCompleted) {
        String query = "UPDATE tasks SET taskname = ?, description = ?, hoursspent = ?, iscompleted = ? WHERE taskid = ?";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);

            // Debugging logs
            System.out.println("Query: " + query);
            System.out.println("taskname: " + taskname + ", description: " + description + ", hoursspent: " + hoursspent + ", isCompleted: " + isCompleted + ", taskId: " + taskId);

            preparedStatement.setString(1, taskname);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, hoursspent);
            preparedStatement.setBoolean(4, isCompleted);
            preparedStatement.setInt(5, taskId);
            int rowsAffected = preparedStatement.executeUpdate();

            // Log antal opdaterede rækker
            System.out.println("Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteTask (int taskId) {
        String query = "DELETE FROM tasks WHERE taskid = ?";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, taskId);
            preparedStatement.executeQuery();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    public TaskModel getTaskById(int taskId) {
        String query = "select * from tasks where taskid = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1,taskId);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return new TaskModel(
                            rs.getInt("subtaskid"),
                            rs.getInt("taskid"),
                            rs.getString("taskName"),
                            rs.getString("description"),
                            rs.getInt("hoursspent"),
                            rs.getBoolean("iscompleted")
                    );
                } else {
                   return null;
                }
            }

        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }
}