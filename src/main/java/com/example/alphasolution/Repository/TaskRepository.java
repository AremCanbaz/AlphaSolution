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
}
