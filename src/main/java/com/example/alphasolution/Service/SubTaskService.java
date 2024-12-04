package com.example.alphasolution.Service;

import com.example.alphasolution.Model.SubTaskModel;
import com.example.alphasolution.Repository.SubTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SubTaskService {
    @Autowired
    SubTaskRepository subTaskRepository;

    public ArrayList<SubTaskModel> getAllSubTasks(int projectid) {
        return subTaskRepository.getSubTaskesById(projectid);
    }
}
