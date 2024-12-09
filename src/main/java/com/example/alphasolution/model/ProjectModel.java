package com.example.alphasolution.model;

public class ProjectModel {
    private int projectId;
    private String projectName;
    private String description;
    private int totalHours;
    private boolean iscompleted;
    private int workingDays;

    // Konstruktør til ProjektModellen
    public ProjectModel(int projectId, String projectName, String description, int totalHours, boolean iscompleted, int workingDays) {
        this.projectId = projectId;
        this.projectName = projectName;
        this.description = description;
        this.totalHours = totalHours;
        this.iscompleted = iscompleted;
        this.workingDays = workingDays;
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

    public int getProjectId() {
        return projectId;
    }
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }
    public boolean isCompleted() {
        return iscompleted;
    }
    public void setCompleted(boolean iscompleted) {
        this.iscompleted = iscompleted;
    }
    public int getWorkingDays() {
        return workingDays;
    }
    public void setWorkingDays(int workingDays) {
        this.workingDays = workingDays;
    }
    public double getHoursPrDay() {
        if (workingDays != 0 && totalHours > 0) {
            return (double) totalHours / workingDays;
        }
        return 0;

    }
}
