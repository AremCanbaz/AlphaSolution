package com.example.alphasolution.Repository;

import com.example.alphasolution.Model.ProjectModel;
import com.example.alphasolution.Model.SubTaskModel;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subTasks;
    }

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

    public void getTotalHours(int subtaskId) {
        String query = "SELECT SUM(hoursspent) AS total_hours FROM tasks WHERE subtaskid = ?";
        int totalHours = 0;

        // Beregn total_hours fra tasks-tabellen
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1, subtaskId);
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
            String query2 = "UPDATE subtasks SET totalhours = ? WHERE subtaskid = ?";
            PreparedStatement updateStatement = con.prepareStatement(query2);
            updateStatement.setInt(1, totalHours);
            updateStatement.setInt(2, subtaskId);

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
                    // Returnér null, hvis ingen rækker blev fundet
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
    public boolean areAllSubtasksCompleted(int projectId) {
            // SQL-forespørgsel for at hente "is_completed" for alle opgaver i delprojektet
            String query = "SELECT IsCompleted FROM subtasks WHERE subtaskid = ?";
            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setInt(1, projectId);
                ResultSet rs = ps.executeQuery();

                // Læs alle opgaver
                while (rs.next()) {
                    boolean isCompleted = rs.getBoolean("IsCompleted");

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
    public void updateProjectStatusIfAllSubtasksCompleted(int projectId) {
        if (areAllSubtasksCompleted(projectId)) {
            String query = "UPDATE projects SET Description = 'Alle del projekter færdig', IsCompleted = ? WHERE projectid = ?";

            try (PreparedStatement ps = con.prepareStatement(query)) {
                ps.setBoolean(1, areAllSubtasksCompleted(projectId));
                ps.setInt(2, projectId);
                ps.executeUpdate();
                System.out.println("Project " + projectId + " marked as completed.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
