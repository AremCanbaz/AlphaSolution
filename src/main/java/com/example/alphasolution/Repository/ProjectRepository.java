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
    public void createProject(String projectName, String description, int userId) {
        String createQuery = "INSERT INTO projects (projectname, description) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(createQuery);
            preparedStatement.setString(1, projectName);
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
            preparedStatement.setInt(1,projectId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
