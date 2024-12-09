package com.example.alphasolution.Service;

import com.example.alphasolution.model.TaskModel;
import com.example.alphasolution.Repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TaskService {
    @Autowired
    TaskRepository taskRepository;

    public ArrayList<TaskModel> getTasks(int subTaskId) {
        return taskRepository.getTaskesById(subTaskId);
    }
    public void createtask (int subtaskid, String description, String taskname, int hoursspent, int workingDays){
        taskRepository.createTask(subtaskid, description, taskname, hoursspent,workingDays);
    }
    public void deletetask(int taskid){
        taskRepository.deleteTask(taskid);
    }
    public void updatetask(int taskid, String taskname, String description, int hoursspent, boolean iscompleted,int workingDays) {
        System.out.println("Updating task: " + taskid);
        System.out.println("taskname: " + taskname + ", description: " + description + ", hoursspent: " + hoursspent + ", iscompleted: " + iscompleted + ", workingDays: " + workingDays);

        taskRepository.editTask(taskid, taskname, description, hoursspent, iscompleted,workingDays);
    }
    public TaskModel getTask(int taskid){
       return taskRepository.getTaskById(taskid);
    }
    public int getProjectIdBySubtaskId(int subTaskId){
        return taskRepository.getProjectIdbySubtaskId(subTaskId);
    }
    public void updateSubTaskToTrueIfAllTaskAreDone(int subTaskId){
        taskRepository.updateSubProjectCompletion(subTaskId);
    }
}
