package com.example.alphasolution.Service;

import com.example.alphasolution.Model.SubTaskModel;
import com.example.alphasolution.Repository.SubTaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubTaskService {
    @Autowired
    SubTaskRepository subTaskRepository;

    public List<SubTaskModel> getAllSubTasks(int projectid) {
        return subTaskRepository.getSubTasksByProjectId(projectid); // Add a repository method for this
    }


    public void deleteSubTask(int subtaskId){
        subTaskRepository.deleteSubTask(subtaskId);
    }
    public String getSubTaskName(int subtaskId) {
        return subTaskRepository.getSubTaskNameById(subtaskId);
    }

    public void createSubTask(int projectid, String subtaskName, String subtaskDesc) {
        subTaskRepository.createSubTask(projectid, subtaskName, subtaskDesc);
    }

    public void updateSubTask(int subTaskId, String subTaskName, String subTaskDescription) {
        subTaskRepository.updateSubTask(subTaskId, subTaskName, subTaskDescription);
    }



    public void getTotalHoursForSubTask(int subtaskId) {
        subTaskRepository.getTotalHours(subtaskId);
    }
    public void editSubTask(int subtaskId, String subtaskName, String subtaskDesc) {
        subTaskRepository.editSubtask(subtaskId, subtaskName, subtaskDesc);
    }
   public SubTaskModel findSubTaskById(int subtaskId) {
        return subTaskRepository.findSubTaskBySubTaskId(subtaskId);
   }
}

