package com.example.alphasolution.Repository;

import com.example.alphasolution.model.SubTaskModel;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

@Repository
public class SubTaskRepository {
    // Henter connection til databasen fra DBMANAGER
    Connection con = DbManager.getConnection();

    // Metode til at hente alle delprojekter fra databasen
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
                        rs.getBoolean("iscompleted"),
                        rs.getInt("WorkingDays")
                );
                subTasks.add(subTask);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subTasks;
    }
    // Metode til at slette delprojekt vha af delprojekt id
    public void deleteSubTask(int subTaskId) {
        String deleteQuery = "DELETE FROM subtasks WHERE subtaskid = ?";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, subTaskId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // henter navnet på delprojektet vha af delprojektid
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
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }
    //Metode til at oprette delprojekt og sætte ind i databasen.
    public void createSubTask(int projectid, String subtaskdescription, String subtaskname) {
        String query1 = "INSERT INTO subtasks (projectid, subtaskname, subtaskdescription) VALUES (?,?,?)";
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query1);
            preparedStatement.setInt(1, projectid);
            preparedStatement.setString(2, subtaskdescription);
            preparedStatement.setString(3, subtaskname);
            preparedStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    // metode til at ændre total timer fra opgaverne tilknyttet delprojektet og opdatere dem i databasen.
    public void getTotalHoursAndWorkingDays(int subtaskId) {
        String query = "SELECT SUM(hoursspent) AS total_hours, SUM(WorkingDays) AS Working_Days FROM tasks WHERE subtaskid = ?";
        int totalHours = 0;
        int workingDays = 0;

        // Beregner total_hours fra tasks-tabellen
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, subtaskId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                totalHours = resultSet.getInt("total_hours");
                workingDays = resultSet.getInt("Working_Days");
            }
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        // Opdatere total_hours i del projekt tabellen(SubtaskTabel)
        try {
            String query2 = "UPDATE subtasks SET totalhours = ?, WorkingDays = ? WHERE subtaskid = ?";
            PreparedStatement updateStatement = con.prepareStatement(query2);
            updateStatement.setInt(1, totalHours);
            updateStatement.setInt(2, workingDays);
            updateStatement.setInt(3, subtaskId);

            updateStatement.executeUpdate(); // Udfør opdatering


            updateStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Metode til at ændre delprojekter og opdatere dem i databasen
    public void editSubtask(int subtaskid, String subtaskName, String description) {
        try {


            String query = "UPDATE subtasks SET subtaskname = ?, SubtaskDescription = ? WHERE subtaskid = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1, subtaskName);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, subtaskid);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Metode til at finde delprojekt vha af delprojektid (SubtaskId)
    public SubTaskModel findSubTaskBySubTaskId(int subtaskid) {
        String query = "SELECT * FROM subtasks WHERE subtaskid = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(query)) {
            // Sæt parameteren i forespørgslen
            preparedStatement.setInt(1, subtaskid);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                // Hvis et resultat findes, opret og returner modellen
                if (rs.next()) {
                    return new SubTaskModel(
                            rs.getInt("subtaskid"),
                            rs.getString("subtaskname"),
                            rs.getString("SubtaskDescription"),
                            rs.getInt("projectid") // Brug userid, hvis det er en kolonne
                    );
                } else {
                    // Returnere null, hvis ingen rækker blev fundet
                    return null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // finder Brugerid vha af projekt id fra databasen
    public int getUserIdbyProjectId(int projectid) {
        String query = "SELECT userid FROM projects WHERE projectid = ?";
        int userId = 0;
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, projectid);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Flyt cursoren til første række
            if (resultSet.next()) {
                userId = resultSet.getInt("userid");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userId;
    }
    // metode til at beregne om alle delprojekter er sande
    public boolean areAllSubtasksCompleted(int projectId) {
        String query = "SELECT IsCompleted FROM subtasks WHERE projectid = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, projectId);
            ResultSet rs = ps.executeQuery();

            // Tjek om alle delprojekter er færdige
            while (rs.next()) {
                if (!rs.getBoolean("IsCompleted")) {
                    return false; // Hvis en delprojekt ikke er færdig
                }
            }
            return true; // Hvis alle delprojekt er færdige
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Standardværdi, hvis noget går galt
    }

    // metode til at opdatere værdien om all deprojekter er færdige eller ej og sætter værdien ind i databsen
    public void updateProjectStatusIfAllSubtasksCompleted(int projectId) {
        boolean allCompleted = areAllSubtasksCompleted(projectId); // Tjek status på alle subtasks

        String query = "UPDATE projects SET IsCompleted = ? WHERE projectid = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setBoolean(1, allCompleted); // Opdater projektets status
            ps.setInt(2, projectId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
