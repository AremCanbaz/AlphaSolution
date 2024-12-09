package com.example.alphasolution.Service;

import com.example.alphasolution.model.SubTaskModel;
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
    public void deleteSubTask(int subtaskId){
        subTaskRepository.deleteSubTask(subtaskId);
    }
    public String getSubtaskName(int subtaskId) {
        return subTaskRepository.getTaskNameById(subtaskId);
    }

    public void createSubTask(int projectid, String subtaskName, String subtaskDesc) {
        subTaskRepository.createSubTask(projectid, subtaskName, subtaskDesc);
    }
    public void getTotalHoursForSubTask(int subtaskId) {
        subTaskRepository.getTotalHoursAndWorkingDays(subtaskId);
    }
    public void editSubTask(int subtaskId, String subtaskName, String subtaskDesc) {
        subTaskRepository.editSubtask(subtaskId, subtaskName, subtaskDesc);
    }
   public SubTaskModel findSubTaskById(int subtaskId) {
        return subTaskRepository.findSubTaskBySubTaskId(subtaskId);
   }
   public int getUseridByProjectId(int projectid) {
        return subTaskRepository.getUserIdbyProjectId(projectid);
   }
   public void updateProjectIscomplete(int projectid) {
        subTaskRepository.updateProjectStatusIfAllSubtasksCompleted(projectid);
   }
}
