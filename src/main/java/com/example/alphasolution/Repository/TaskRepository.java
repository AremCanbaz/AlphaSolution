package com.example.alphasolution.Repository;


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
            String query = "SELECT * FROM tasks WHERE subtaskid = ?";
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
            System.out.println(e.getMessage());
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
            preparedStatement.execute();
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
    public int getProjectIdbySubtaskId(int subtaskId) {
        String query = "select projectid from subtasks where subtaskid = ?";
        int projectId = 0;

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1,subtaskId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                projectId = resultSet.getInt("projectid");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return projectId;
    }
    public boolean areAllTasksCompleted(int subtaskid) {
        // SQL-forespørgsel for at hente "is_completed" for alle opgaver i delprojektet
        String query = "SELECT IsCompleted FROM Tasks WHERE subtaskid = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, subtaskid);
            ResultSet rs = ps.executeQuery();

            // Læs alle opgaver
            while (rs.next()) {
                boolean isCompleted = rs.getBoolean("iscompleted");

                // Hvis én opgave ikke er færdig, returner false
                if (!isCompleted) {
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Hvis vi når hertil, betyder det, at alle opgaver er færdige
        return true;
    }
    public void updateSubProjectCompletion(int subProjectId) {
        // Tjek, om alle opgaver er færdige
        boolean allTasksCompleted = areAllTasksCompleted(subProjectId);

        // Opdater delprojektets status
        String query = "UPDATE subtasks SET iscompleted = ? WHERE subtaskid = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setBoolean(1, allTasksCompleted); // Sæt status til true eller false
            ps.setInt(2, subProjectId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}