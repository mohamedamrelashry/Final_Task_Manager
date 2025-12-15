package com.taskmanagement.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Project implements Serializable {
    private String id;
    private String name;
    private String description;
    private String customerName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status; // ACTIVE, COMPLETED, ON_HOLD, CANCELLED
    private boolean active;

    public Project() {
    }

    public Project(String id, String name, String description, String customerName, 
                   LocalDateTime startDate, LocalDateTime endDate, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.customerName = customerName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.active = true;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return name;
    }
}
