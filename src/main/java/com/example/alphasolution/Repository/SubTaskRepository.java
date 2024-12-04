package com.example.alphasolution.Repository;

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
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return subTasks;
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
        }
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return null;
    }
}