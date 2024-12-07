package com.example.alphasolution.Repository;


import com.example.alphasolution.model.TaskModel;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository
public class TaskRepository {
    Connection con = DbManager.getConnection();

    //Metode til at hente alle opgaverne tilknyttet delprojektet
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
    //Metode til at oprette opgave til delprojketet
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
    //Metode til at ændre delprojektet og sende info til databasen
    public void editTask(int taskId, String taskname, String description, int hoursspent, boolean isCompleted) {
        String query = "UPDATE tasks SET taskname = ?, description = ?, hoursspent = ?, iscompleted = ? WHERE taskid = ?";

        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);

            // Debugging logs

            preparedStatement.setString(1, taskname);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, hoursspent);
            preparedStatement.setBoolean(4, isCompleted);
            preparedStatement.setInt(5, taskId);
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Metode til at slette opgave
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

    //metode til at hente opgave af opgaveid
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

    //Metode til at hente projekt id vha af delprojekt id fra databasen
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
    //Metode til at hente alle opgaverne tilknyttet delprojektet og tjekke dens værdi om true eller false
    public boolean areAllTasksCompleted(int subtaskid) {
        // SQL kode for at hente "is_completed" for alle opgaver i delprojektet
        String query = "SELECT IsCompleted FROM Tasks WHERE subtaskid = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, subtaskid);
            ResultSet rs = ps.executeQuery();

            // Læser alle opgaver
            while (rs.next()) {
                boolean isCompleted = rs.getBoolean("iscompleted");

                // Hvis en opgave ikke er færdig, returner false
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
    //Metode til at sætte værdien af opgaverne i delprojekt tabellen(SubtaskTabel)
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