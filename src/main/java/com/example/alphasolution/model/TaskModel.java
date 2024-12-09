package com.example.alphasolution.model;

public class TaskModel {
    int subTaskId;
    int taskid;
    String taskName;
    String description;
    int hoursspent;
    Boolean iscompleted;
    int workingDays;

    //Konstruktør til opgave
    public TaskModel(int taskid, int subTaskId, String taskName, String description, int hoursspent, boolean iscompleted, int workingDays) {
        this.subTaskId = subTaskId;
        this.taskid = taskid;
        this.taskName = taskName;
        this.description = description;
        this.hoursspent = hoursspent;
        this.iscompleted = iscompleted;
        this.workingDays = workingDays;
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
    public void setTaskid(int taskid) {
        this.taskid = taskid;
    }
    public String getTaskName() {
        return taskName;
    }
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    public String getDescription() {
        return description;
    }
    public void setTaskDescription(String taskDescription) {
        this.description = taskDescription;
    }
    public int getHoursspent() {
        return hoursspent;
    }
    public void setHoursspent(int hoursspent) {
        this.hoursspent = hoursspent;
    }
    public Boolean getIscompleted() {
        return iscompleted;
    }
    public void setIscompleted(Boolean iscompleted) {
        this.iscompleted = iscompleted;
    }
    public int getWorkingDays() {
        return workingDays;
    }
    public void setWorkingDays(int workingDays) {
        this.workingDays = workingDays;
    }
    public double getHoursPrDay(){
        if(workingDays !=0 && hoursspent >0){
            return(double)hoursspent/workingDays;
        }
        return 0;
    }


}
