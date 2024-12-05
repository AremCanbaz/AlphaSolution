package com.example.alphasolution.Model;

public class TaskModel {
    int subTaskId;
    int taskId;
    String taskName;
    String taskDescription;
    int taskTime;
    Boolean taskStatus;

    public TaskModel(int subTaskId, int taskid, String taskName, String taskDescription, int taskTime, boolean taskStatus) {
        this.subTaskId = subTaskId;
        this.taskid = taskid;

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

    public int getTaskid() {
        return taskid;
    }
    public void setTaskid(int subTaskid) {
        this.taskid = taskid;

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
