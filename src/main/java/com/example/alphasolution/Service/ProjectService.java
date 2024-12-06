package com.example.alphasolution.Service;

import com.example.alphasolution.model.ProjectModel;
import com.example.alphasolution.Repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service

public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;

public ArrayList<ProjectModel> projectList(int userId){
    return projectRepository.getAllProjectsById(userId);
}
public void createProject(String projectName, String description, int userId){
    projectRepository.createProject(projectName, description, userId);
}

// Metode til søgefunktion
    public ProjectModel getProjectByName(String projectName, ArrayList<ProjectModel> projects) {


        for (ProjectModel project : projects) {
            if (project.getProjectName().equalsIgnoreCase(projectName)) {
                return project;
            }
        }
        return null;
    }
public void deleteProject(int projectId){
    projectRepository.deleteProject(projectId);
}

public String getProjectName(int projectId){
    return projectRepository.getProjectNameById(projectId);
}
public void getTotalHours(int projectId){
    projectRepository.getTotalHours(projectId);
}

public void editProject(String projectName, String description, int projectId){
    projectRepository.editProject(projectId, projectName, description);
}
public ProjectModel getProjectById(int projectId){
    return projectRepository.findById(projectId);
}

}
