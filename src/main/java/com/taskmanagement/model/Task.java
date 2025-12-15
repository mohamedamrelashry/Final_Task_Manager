package com.taskmanagement.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Task implements Serializable {
    private String code;
    private String title;
    private String description;
    private String assignedEmployeeId;
    private String taskPhase; // PENDING, UNDER_WORK, TEST, EVALUATION, CANCELED, COMPLETED
    private String projectId;
    private String priority; // LOW, MEDIUM, HIGH, CRITICAL
    private String creatorId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private double estimationHours;
    private double actualHours;

    public Task() {
    }

    public Task(String code, String title, String description, String assignedEmployeeId, 
                String taskPhase, String projectId, String priority, String creatorId, 
                LocalDateTime startDate, LocalDateTime endDate, double estimationHours) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.assignedEmployeeId = assignedEmployeeId;
        this.taskPhase = taskPhase;
        this.projectId = projectId;
        this.priority = priority;
        this.creatorId = creatorId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.estimationHours = estimationHours;
        this.actualHours = 0.0;
    }

    // Getters and Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAssignedEmployeeId() {
        return assignedEmployeeId;
    }

    public void setAssignedEmployeeId(String assignedEmployeeId) {
        this.assignedEmployeeId = assignedEmployeeId;
    }

    public String getTaskPhase() {
        return taskPhase;
    }

    public void setTaskPhase(String taskPhase) {
        this.taskPhase = taskPhase;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public double getEstimationHours() {
        return estimationHours;
    }

    public void setEstimationHours(double estimationHours) {
        this.estimationHours = estimationHours;
    }

    public double getActualHours() {
        return actualHours;
    }

    public void setActualHours(double actualHours) {
        this.actualHours = actualHours;
    }

    @Override
    public String toString() {
        return "Task{" +
                "code='" + code + '\'' +
                ", title='" + title + '\'' +
                ", assignedEmployeeId='" + assignedEmployeeId + '\'' +
                ", taskPhase='" + taskPhase + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }
}
