package com.example.alphasolution.Repository;

import com.example.alphasolution.Model.ProjectModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@Repository

public class ProjectRepository {
    Connection connection = DbManager.getConnection();

    // Metode til at hente alle projekter tilknyttet den enkelte bruger.
    public ArrayList<ProjectModel> getAllProjectsById(int userId) {
        ArrayList<ProjectModel> projects = new ArrayList<>();
        try {
            String getProjectsQuery = "SELECT * FROM projects WHERE UserId = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(getProjectsQuery);
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ProjectModel projectModel = new ProjectModel(
                        resultSet.getInt("projectid"),
                        resultSet.getString("projectname"),
                        resultSet.getString("description"),
                        resultSet.getInt("totalhours")
                );
                projects.add(projectModel);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return projects;
    }

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

    public void getTotalHours(int projectId) {
        String query = "SELECT SUM(totalhours) AS total_hours FROM subtasks WHERE projectid = ?";
        int totalHours = 0;

        // Beregn total_hours fra tasks-tabellen
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, projectId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                totalHours = resultSet.getInt("total_hours");
            }
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

            int rowsUpdated = updateStatement.executeUpdate(); // Udfør opdatering
            if (rowsUpdated > 0) {
                System.out.println("Total hours updated successfully.");
            } else {
                System.out.println("No rows were updated.");
            }

            updateStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
                            rs.getInt("userid") // Brug userid, hvis det er en kolonne
                    );
                } else {
                    // Returnér null, hvis ingen rækker blev fundet
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
