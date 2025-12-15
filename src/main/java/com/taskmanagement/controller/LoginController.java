package com.taskmanagement.controller;

import com.taskmanagement.model.Employee;
import com.taskmanagement.model.User;
import com.taskmanagement.util.FileManager;
import com.taskmanagement.util.PasswordUtil;
import com.taskmanagement.util.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.List;

/**
 * Controller for the Login view.
 * Handles user authentication and navigation to the main dashboard.
 */
public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Label messageLabel;

    @FXML
    public void initialize() {
        loginButton.setOnAction(e -> handleLogin());
        // Allow Enter key to trigger login
        passwordField.setOnAction(e -> handleLogin());
    }

    /**
     * Handles the login button click event.
     * Authenticates the user and navigates to the main dashboard if successful.
     */
    @FXML
    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            showError("Please enter username and password");
            return;
        }

        List<User> users = FileManager.loadUsers();
        User authenticatedUser = null;

        // Find and authenticate user
        for (User user : users) {
            if (user.getUsername().equals(username) && user.isActive()) {
                if (PasswordUtil.checkPassword(password, user.getPassword())) {
                    authenticatedUser = user;
                    break;
                }
            }
        }

        if (authenticatedUser != null) {
            // Set current user in session
            SessionManager.getInstance().setCurrentUser(authenticatedUser);
            
            // Load employee data if exists
            List<Employee> employees = FileManager.loadEmployees();
            for (Employee emp : employees) {
                if (emp.getUserId() != null && emp.getUserId().equals(authenticatedUser.getId())) {
                    SessionManager.getInstance().setCurrentEmployee(emp);
                    break;
                }
            }

            showSuccess("Login successful!");
            navigateToDashboard();
        } else {
            showError("Invalid username or password");
        }
    }

    /**
     * Navigates to the main dashboard after successful login.
     */
    private void navigateToDashboard() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));
            Scene scene = new Scene(loader.load(), 1200, 700);
            
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setTitle("Task Management System - Dashboard");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            showError("Error loading dashboard: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Displays an error message to the user.
     */
    private void showError(String message) {
        messageLabel.setStyle("-fx-text-fill: red;");
        messageLabel.setText(message);
    }

    /**
     * Displays a success message to the user.
     */
    private void showSuccess(String message) {
        messageLabel.setStyle("-fx-text-fill: green;");
        messageLabel.setText(message);
    }
}
