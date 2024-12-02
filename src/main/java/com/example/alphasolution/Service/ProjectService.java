package com.example.alphasolution.Service;

import com.example.alphasolution.Model.ProjectModel;
import com.example.alphasolution.Repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service

public class ProjectService {
    ProjectRepository projectRepository;

public ArrayList<ProjectModel> projectList(int userId){
    return projectRepository.getAllProjectsById(userId);
}
public void createProject(String projectName, String description, int userId){
    projectRepository.createProject(projectName, description, userId);
}
public void deleteProject(int projectId){
    projectRepository.deleteProject(projectId);
}
}