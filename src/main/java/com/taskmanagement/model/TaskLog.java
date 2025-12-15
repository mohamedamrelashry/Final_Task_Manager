package com.taskmanagement.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TaskLog implements Serializable {
    private String id;
    private String taskCode;
    private String employeeId;
    private LocalDateTime fromTime;
    private LocalDateTime toTime;
    private double hoursSpent;
    private String notes;

    public TaskLog() {
    }

    public TaskLog(String id, String taskCode, String employeeId, LocalDateTime fromTime, 
                   LocalDateTime toTime, String notes) {
        this.id = id;
        this.taskCode = taskCode;
        this.employeeId = employeeId;
        this.fromTime = fromTime;
        this.toTime = toTime;
        this.notes = notes;
        calculateHoursSpent();
    }

    private void calculateHoursSpent() {
        if (fromTime != null && toTime != null) {
            long minutes = java.time.Duration.between(fromTime, toTime).toMinutes();
            this.hoursSpent = minutes / 60.0;
        }
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

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDateTime getFromTime() {
        return fromTime;
    }

    public void setFromTime(LocalDateTime fromTime) {
        this.fromTime = fromTime;
        calculateHoursSpent();
    }

    public LocalDateTime getToTime() {
        return toTime;
    }

    public void setToTime(LocalDateTime toTime) {
        this.toTime = toTime;
        calculateHoursSpent();
    }

    public double getHoursSpent() {
        return hoursSpent;
    }

    public void setHoursSpent(double hoursSpent) {
        this.hoursSpent = hoursSpent;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "TaskLog{" +
                "id='" + id + '\'' +
                ", taskCode='" + taskCode + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", hoursSpent=" + hoursSpent +
                '}';
    }
}
