package com.example.alphasolution.Repository;

import com.example.alphasolution.model.ProjectModel;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository


public class ProjectRepository {
    //Henter databasen connection fr DbManager klassen
    private Connection connection = DbManager.getConnection();

    // Metode til at hente alle projekter tilknyttet den enkelte bruger.
    public ArrayList<ProjectModel> getAllProjectsById(int userId) {
        //Arrayliste tilføjet for at kunne tilføje og returnere den
        ArrayList<ProjectModel> projects = new ArrayList<>();
        try {
            //SQL Koden til databasen for at hente projekterne
            String getProjectsQuery = "SELECT * FROM projects WHERE UserId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(getProjectsQuery);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            // While loop brugt til at hente alle projekterne
            while (resultSet.next()) {
                //Projektmodellen
                ProjectModel projectModel = new ProjectModel(
                        resultSet.getInt("projectid"),
                        resultSet.getString("projectname"),
                        resultSet.getString("description"),
                        resultSet.getInt("totalhours"),
                        resultSet.getBoolean("IsCompleted")
                );
                //Tilføj alle projekter til arraylisten ovenfor.
                projects.add(projectModel);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return projects;
    }

    // Metode til at oprette projekter til databasen
    public void createProject(String projectname, String description, int userId) {
        String createQuery = "INSERT INTO projects (projectname, description, userId) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(createQuery);
            preparedStatement.setString(1, projectname);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, userId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    // Metode til at slette projekter til databasen
    public void deleteProject(int projectId) {
        String deleteQuery = "DELETE FROM projects WHERE projectid = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery);
            preparedStatement.setInt(1, projectId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // Metode til at hente projektnavn vha projektid
    public String getProjectNameById(int projectId) {
        String query = "SELECT projectname FROM projects WHERE projectid = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, projectId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("projectname");
                }

            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }
    //metode til at regne alle timer ud for et projekt af delprojekterne vha af project id.
    public void getTotalHours(int projectId) {
        String query = "SELECT SUM(totalhours) AS total_hours FROM subtasks WHERE projectid = ?";
        int totalHours = 0;

        // Beregner total_hours fra subtasks-tabellen
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, projectId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                totalHours = resultSet.getInt("total_hours");
            }
            // Lukker begge metoder
            resultSet.close();
            preparedStatement.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        // Opdater total_hours i subtasks-tabellen
        try {
            String query2 = "UPDATE projects SET totalhours = ? WHERE projectid = ?";
            PreparedStatement updateStatement = connection.prepareStatement(query2);
            updateStatement.setInt(1, totalHours);
            updateStatement.setInt(2, projectId);

            updateStatement.executeUpdate(); // Udfør opdatering


            updateStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //Metode til at ændre projektet og indsætte ændringerne i databasen
    public void editProject(int projectId, String projectname, String description) {

        try {
            String query = "UPDATE projects SET projectname = ?, description = ? WHERE projectid = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, projectname);
            preparedStatement.setString(2, description);
            preparedStatement.setInt(3, projectId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Metode til at finde projektet vha af project id
    public ProjectModel findById(int projectid){
        String query = "SELECT * FROM projects WHERE projectid = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            // Sæt parameteren i forespørgslen
            preparedStatement.setInt(1, projectid);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                // Hvis et resultat findes, opret og returner modellen
                if (rs.next()) {
                    return new ProjectModel(
                            rs.getInt("projectid"),
                            rs.getString("projectname"),
                            rs.getString("description"),
                            rs.getInt("userid"),
                            // Brug userid, hvis det er en kolonne
                            rs.getBoolean("IsCompleted")
                    );
                } else {
                    // Returnere null, hvis ingen rækker blev fundet
                    return null;
                }
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
