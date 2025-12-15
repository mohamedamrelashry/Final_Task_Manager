package com.taskmanagement;

import com.taskmanagement.model.User;
import com.taskmanagement.util.FileManager;
import com.taskmanagement.util.PasswordUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;
import java.util.UUID;

/**
 * Main application entry point for the Task Management System.
 * Initializes default data and launches the login screen.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Initialize default data if needed
        initializeDefaultData();
        
        // Load and show the login screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        Scene scene = new Scene(loader.load(), 500, 400);
        
        primaryStage.setTitle("Task Management System - Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Initializes default admin user if no users exist in the system.
     */
    private void initializeDefaultData() {
        List<User> users = FileManager.loadUsers();
        if (users.isEmpty()) {
            User admin = new User(
                UUID.randomUUID().toString(),
                "admin",
                PasswordUtil.hashPassword("admin123"),
                "ADMIN",
                "admin@taskmanagement.com"
            );
            users.add(admin);
            FileManager.saveUsers(users);
            System.out.println("✓ Default admin user created: username=admin, password=admin123");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
