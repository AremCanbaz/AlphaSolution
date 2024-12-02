package com.example.alphasolution.Model;

public class ProjectModel {
    private String projectName;
    private String description;
    private int totalHours;

    public ProjectModel() {}

    public ProjectModel(String projectName, String description, int totalHours) {
        this.projectName = projectName;
        this.description = description;
        this.totalHours = totalHours;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(int totalHours) {
        this.totalHours = totalHours;
    }
}
