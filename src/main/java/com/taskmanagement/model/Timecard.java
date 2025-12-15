package com.taskmanagement.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Timecard implements Serializable {
    private String id;
    private String employeeId;
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
    private String notes;

    public Timecard() {
    }

    public Timecard(String id, String employeeId, LocalDateTime arrivalTime, LocalDateTime departureTime, String notes) {
        this.id = id;
        this.employeeId = employeeId;
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.notes = notes;
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

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Timecard{" +
                "id='" + id + '\'' +
                ", employeeId='" + employeeId + '\'' +
                ", arrivalTime=" + arrivalTime +
                ", departureTime=" + departureTime +
                '}';
    }
}
