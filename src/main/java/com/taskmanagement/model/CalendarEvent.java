package com.taskmanagement.model;

import java.io.Serializable;
import java.time.LocalDate;

public class CalendarEvent implements Serializable {
    private String id;
    private String taskCode;
    private String taskTitle;
    private LocalDate date;
    private String phase;
    private String assignedEmployeeId;
    private String priority;

    public CalendarEvent() {
    }

    public CalendarEvent(String id, String taskCode, String taskTitle, LocalDate date, 
                        String phase, String assignedEmployeeId, String priority) {
        this.id = id;
        this.taskCode = taskCode;
        this.taskTitle = taskTitle;
        this.date = date;
        this.phase = phase;
        this.assignedEmployeeId = assignedEmployeeId;
        this.priority = priority;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskCode() {
        return taskCode;
    }

    public void setTaskCode(String taskCode) {
        this.taskCode = taskCode;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getAssignedEmployeeId() {
        return assignedEmployeeId;
    }

    public void setAssignedEmployeeId(String assignedEmployeeId) {
        this.assignedEmployeeId = assignedEmployeeId;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "CalendarEvent{" +
                "taskCode='" + taskCode + '\'' +
                ", taskTitle='" + taskTitle + '\'' +
                ", date=" + date +
                ", phase='" + phase + '\'' +
                '}';
    }
}
