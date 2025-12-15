package com.taskmanagement.model;

import java.io.Serializable;

public class Employee implements Serializable {
    private String id;
    private String name;
    private String email;
    private String phone;
    private String employeeType; // Developer, Designer, QA, Manager, etc.
    private String userId; // Reference to User
    private String department;
    private double salary;
    private boolean active;

    public Employee() {
    }

    public Employee(String id, String name, String email, String phone, String employeeType, String userId, String department, double salary) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.employeeType = employeeType;
        this.userId = userId;
        this.department = department;
        this.salary = salary;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(String employeeType) {
        this.employeeType = employeeType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", employeeType='" + employeeType + '\'' +
                ", department='" + department + '\'' +
                ", active=" + active +
                '}';
    }
}
