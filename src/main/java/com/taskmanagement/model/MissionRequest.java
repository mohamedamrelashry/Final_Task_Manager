package com.taskmanagement.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class MissionRequest implements Serializable {
    private String id;
    private String employeeId;
    private String type; // MISSION, PERMISSION
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String destination;
    private String purpose;
    private String status; // PENDING, APPROVED, REJECTED
    private String reviewedBy;
    private String reviewNotes;

    public MissionRequest() {
    }

    public MissionRequest(String id, String employeeId, String type, LocalDateTime startTime, 
                          LocalDateTime endTime, String destination, String purpose) {
        this.id = id;
        this.employeeId = employeeId;
        this.type = type;
        this.startTime = startTime;
        this.endTime = endTime;
        this.destination = destination;
        this.purpose = purpose;
        this.status = "PENDING";
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(String reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public String getReviewNotes() {
        return reviewNotes;
    }

    public void setReviewNotes(String reviewNotes) {
        this.reviewNotes = reviewNotes;
    }

    @Override
    public String toString() {
        return "MissionRequest{" +
                "id='" + id + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", type='" + type + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
