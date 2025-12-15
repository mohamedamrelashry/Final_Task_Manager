package com.taskmanagement.controller;

import com.taskmanagement.util.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Controller for the main Dashboard view.
 * Manages navigation between different modules (Admin, Tasks, Employee).
 */
public class DashboardController {

    @FXML
    private BorderPane mainLayout;

    @FXML
    private VBox sidebar;

    @FXML
    private VBox contentArea;

    @FXML
    public void initialize() {
        updateSidebar();
        showWelcome();
    }

    /**
     * Updates the sidebar based on the current user's role.
     */
    private void updateSidebar() {
        SessionManager session = SessionManager.getInstance();
        
        // Clear existing content
        sidebar.getChildren().clear();

        // Admin Module
        if (session.isAdmin()) {
            addSectionLabel("Admin Module");
            addSidebarButton("Manage Users", e -> loadView("/fxml/admin/users.fxml"));
            addSidebarButton("Manage Employees", e -> loadView("/fxml/admin/employees.fxml"));
            addSidebarButton("Manage Projects", e -> loadView("/fxml/admin/projects.fxml"));
            addSeparator();
        }

        // Tasks Module
        if (session.isAdmin() || session.isLeader()) {
            addSectionLabel("Tasks Module");
            addSidebarButton("All Tasks", e -> loadView("/fxml/tasks/all_tasks.fxml"));
            addSidebarButton("Create Task", e -> loadView("/fxml/tasks/create_task.fxml"));
            addSidebarButton("Task Logs", e -> loadView("/fxml/tasks/task_logs.fxml"));
            addSeparator();
        }

        // Calendar
        addSidebarButton("Calendar", e -> loadView("/fxml/tasks/calendar.fxml"));
        addSeparator();

        // My Tasks
        if (session.isEmployee() || session.isLeader()) {
            addSidebarButton("My Tasks", e -> loadView("/fxml/tasks/my_tasks.fxml"));
            addSeparator();
        }

        // Employee Module
        addSectionLabel("Employee Module");
        addSidebarButton("Timecards", e -> loadView("/fxml/employee/timecards.fxml"));
        addSidebarButton("Leave Request", e -> loadView("/fxml/employee/leave_request.fxml"));
        addSidebarButton("Mission/Permission", e -> loadView("/fxml/employee/mission_request.fxml"));

        // Approval
        if (session.isAdmin() || session.isLeader()) {
            addSeparator();
            addSidebarButton("Approve Requests", e -> loadView("/fxml/employee/approvals.fxml"));
        }

        // Logout
        addSeparator();
        addSidebarButton("Logout", e -> logout());
    }

    /**
     * Adds a section label to the sidebar.
     */
    private void addSectionLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 12;");
        sidebar.getChildren().add(label);
    }

    /**
     * Adds a button to the sidebar.
     */
    private void addSidebarButton(String text, javafx.event.EventHandler<javafx.event.ActionEvent> action) {
        javafx.scene.control.Button button = new javafx.scene.control.Button(text);
        button.setPrefWidth(180);
        button.setStyle("-fx-background-color: #34495e; -fx-text-fill: white;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #1abc9c; -fx-text-fill: white;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #34495e; -fx-text-fill: white;"));
        button.setOnAction(action);
        sidebar.getChildren().add(button);
    }

    /**
     * Adds a separator to the sidebar.
     */
    private void addSeparator() {
        javafx.scene.control.Separator separator = new javafx.scene.control.Separator();
        sidebar.getChildren().add(separator);
    }

    /**
     * Shows the welcome screen in the content area.
     */
    private void showWelcome() {
        SessionManager session = SessionManager.getInstance();
        
        VBox welcome = new VBox(20);
        welcome.setPadding(new javafx.geometry.Insets(20));

        Label titleLabel = new Label("Welcome to Task Management System");
        titleLabel.setFont(javafx.scene.text.Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 20));

        Label userLabel = new Label("Logged in as: " + session.getCurrentUser().getUsername());
        userLabel.setFont(javafx.scene.text.Font.font("Arial", 14));

        Label roleLabel = new Label("Role: " + session.getCurrentUser().getRole());
        roleLabel.setFont(javafx.scene.text.Font.font("Arial", 14));

        welcome.getChildren().addAll(titleLabel, userLabel, roleLabel);
        contentArea.getChildren().clear();
        contentArea.getChildren().add(welcome);
    }

    /**
     * Loads a view from an FXML file into the content area.
     */
    private void loadView(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            VBox view = loader.load();
            contentArea.getChildren().clear();
            contentArea.getChildren().add(view);
        } catch (Exception e) {
            showError("Error loading view: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Displays an error message.
     */
    private void showError(String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Logs out the current user and returns to the login screen.
     */
    private void logout() {
        SessionManager.getInstance().logout();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
            Scene scene = new Scene(loader.load(), 500, 400);
            
            Stage stage = (Stage) mainLayout.getScene().getWindow();
            stage.setTitle("Task Management System - Login");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            showError("Error loading login screen: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
