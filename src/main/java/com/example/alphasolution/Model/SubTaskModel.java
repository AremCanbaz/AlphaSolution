package com.example.alphasolution.Model;

public class SubTaskModel {
    private int projectid;
    private int subTaskId;
    private String subTaskName;
    private String subTaskDescription;
    private int subTaskTime;
    private boolean subTaskStatus;


    public SubTaskModel(int projectid, int subTaskId, String subTaskName,String subTaskDescription, int subTaskTime, boolean subTaskStatus){
        this.projectid = projectid;
        this.subTaskId = subTaskId;
        this.subTaskDescription = subTaskDescription;
        this.subTaskName = subTaskName;
        this.subTaskTime = subTaskTime;
        this.subTaskStatus = subTaskStatus;
    }
    public SubTaskModel(int subTaskId, String subTaskName, String subTaskDescription, int projectid){
        this.projectid = projectid;
        this.subTaskId = subTaskId;
        this.subTaskName = subTaskName;
        this.subTaskDescription = subTaskDescription;
    }

    public SubTaskModel(int projectid, String subTaskName, String subtaskdescription) {
        this.projectid = projectid;
        this.subTaskName = subTaskName;
        this.subTaskDescription = subtaskdescription;
    }

    public int getProjectid() {
        return projectid;
    }
    public void setProjectid(int projectid) {
        this.projectid = projectid;
    }
    public int getSubTaskId() {
        return subTaskId;

    }
    public void setSubTaskId(int subTaskId) {
        this.subTaskId = subTaskId;
    }
    public String getSubTaskName() {
        return subTaskName;
    }
    public void setSubTaskName(String subTaskName) {
        this.subTaskName = subTaskName;
    }
    public String getSubTaskDescription() {
        return subTaskDescription;
    }
    public void setSubTaskDescription(String subTaskDescription) {
        this.subTaskDescription = subTaskDescription;
    }
    public int getSubTaskTime() {
        return subTaskTime;
    }
    public void setSubTaskTime(int subTaskTime) {
        this.subTaskTime = subTaskTime;
    }
    public boolean isSubTaskStatus() {
        return subTaskStatus;
    }
    public void setSubTaskStatus(boolean subTaskStatus) {
        this.subTaskStatus = subTaskStatus;
    }
    }

