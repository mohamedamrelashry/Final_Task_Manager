package com.taskmanagement.util;

import com.taskmanagement.model.User;
import com.taskmanagement.model.Employee;

public class SessionManager {
    private static SessionManager instance;
    private User currentUser;
    private Employee currentEmployee;

    private SessionManager() {
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentEmployee(Employee employee) {
        this.currentEmployee = employee;
    }

    public Employee getCurrentEmployee() {
        return currentEmployee;
    }

    public boolean isAdmin() {
        return currentUser != null && "ADMIN".equals(currentUser.getRole());
    }

    public boolean isLeader() {
        return currentUser != null && "LEADER".equals(currentUser.getRole());
    }

    public boolean isEmployee() {
        return currentUser != null && "EMPLOYEE".equals(currentUser.getRole());
    }

    public void logout() {
        currentUser = null;
        currentEmployee = null;
    }
}
