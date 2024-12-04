package com.example.alphasolution.Model;

public class TaskModel {
    int subTaskId;
    int subTaskid;
    String taskName;
    String taskDescription;
    int taskTime;
    Boolean taskStatus;

    public TaskModel(int subTaskId, int subTaskid, String taskName, String taskDescription, int taskTime, boolean taskStatus) {
        this.subTaskId = subTaskId;
        this.subTaskid = subTaskid;
        this.taskName = taskName;
        this.taskDescription = taskDescription;
        this.taskTime = taskTime;
        this.taskStatus = taskStatus;
    }
    public int getSubTaskId() {
        return subTaskId;
    }
    public void setSubTaskId(int subTaskId) {
        this.subTaskId = subTaskId;
    }
    public int getSubTaskid() {
        return subTaskid;
    }
    public void setSubTaskid(int subTaskid) {
        this.subTaskid = subTaskid;
    }
    public String getTaskName() {
        return taskName;
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    public String getTaskDescription() {
        return taskDescription;
    }
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }
    public int getTaskTime() {
        return taskTime;
    }
    public void setTaskTime(int taskTime) {
        this.taskTime = taskTime;
    }
    public Boolean getTaskStatus() {
        return taskStatus;
    }
    public void setTaskStatus(Boolean taskStatus) {
        this.taskStatus = taskStatus;
    }

}
