package com.example.alphasolution.Service;

import com.example.alphasolution.Model.TaskModel;
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
}
