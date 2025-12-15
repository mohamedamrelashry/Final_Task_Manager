package com.taskmanagement.controller.admin;

import com.taskmanagement.model.Project;
import com.taskmanagement.util.FileManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Controller for managing projects (Add, Update, Delete).
 */
public class ProjectManagementController {

    @FXML
    private TableView<Project> projectsTable;

    @FXML
    private TableColumn<Project, String> nameColumn;

    @FXML
    private TableColumn<Project, String> descriptionColumn;

    @FXML
    private TableColumn<Project, String> statusColumn;

    @FXML
    private TextField nameField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Label messageLabel;

    private ObservableList<Project> projectsList;

    @FXML
    public void initialize() {
        setupTableColumns();
        loadProjects();
        setupStatusComboBox();
        setupButtonActions();
    }

    /**
     * Sets up the table columns.
     */
    private void setupTableColumns() {
        nameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));
        descriptionColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDescription()));
        statusColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStatus()));

        projectsTable.setOnMouseClicked(e -> loadSelectedProject());
    }

    /**
     * Loads projects from the file system.
     */
    private void loadProjects() {
        List<Project> projects = FileManager.loadProjects();
        projectsList = FXCollections.observableArrayList(projects);
        projectsTable.setItems(projectsList);
    }

    /**
     * Sets up the status combo box.
     */
    private void setupStatusComboBox() {
        statusComboBox.setItems(FXCollections.observableArrayList("Active", "Completed", "On Hold", "Cancelled"));
    }

    /**
     * Sets up button actions.
     */
    private void setupButtonActions() {
        addButton.setOnAction(e -> addProject());
        updateButton.setOnAction(e -> updateProject());
        deleteButton.setOnAction(e -> deleteProject());
    }

    /**
     * Loads the selected project into the form fields.
     */
    private void loadSelectedProject() {
        Project selectedProject = projectsTable.getSelectionModel().getSelectedItem();
        if (selectedProject != null) {
            nameField.setText(selectedProject.getName());
            descriptionArea.setText(selectedProject.getDescription());
            statusComboBox.setValue(selectedProject.getStatus());
        }
    }

    /**
     * Adds a new project.
     */
    @FXML
    private void addProject() {
        if (!validateForm()) {
            return;
        }

        String name = nameField.getText().trim();
        String description = descriptionArea.getText().trim();
        String status = statusComboBox.getValue();

        java.time.LocalDateTime now = java.time.LocalDateTime.now();
        Project newProject = new Project(
            UUID.randomUUID().toString(),
            name,
            description,
            "", // customerName - can be added later
            now,
            now.plusMonths(1),
            status
        );

        projectsList.add(newProject);
        FileManager.saveProjects(new java.util.ArrayList<>(projectsList));
        clearForm();
        showSuccess("Project added successfully!");
    }

    /**
     * Updates the selected project.
     */
    @FXML
    private void updateProject() {
        Project selectedProject = projectsTable.getSelectionModel().getSelectedItem();
        if (selectedProject == null) {
            showError("Please select a project to update!");
            return;
        }

        if (!validateForm()) {
            return;
        }

        selectedProject.setName(nameField.getText().trim());
        selectedProject.setDescription(descriptionArea.getText().trim());
        selectedProject.setStatus(statusComboBox.getValue());

        FileManager.saveProjects(new java.util.ArrayList<>(projectsList));
        projectsTable.refresh();
        showSuccess("Project updated successfully!");
    }

    /**
     * Deletes the selected project.
     */
    @FXML
    private void deleteProject() {
        Project selectedProject = projectsTable.getSelectionModel().getSelectedItem();
        if (selectedProject == null) {
            showError("Please select a project to delete!");
            return;
        }

        Optional<ButtonType> result = showConfirmation("Are you sure you want to delete this project?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            projectsList.remove(selectedProject);
            FileManager.saveProjects(new java.util.ArrayList<>(projectsList));
            clearForm();
            showSuccess("Project deleted successfully!");
        }
    }

    /**
     * Validates the form fields.
     */
    private boolean validateForm() {
        if (nameField.getText().trim().isEmpty()) {
            showError("Project name cannot be empty!");
            return false;
        }
        if (descriptionArea.getText().trim().isEmpty()) {
            showError("Description cannot be empty!");
            return false;
        }
        if (statusComboBox.getValue() == null) {
            showError("Please select a status!");
            return false;
        }
        return true;
    }

    /**
     * Clears the form fields.
     */
    private void clearForm() {
        nameField.clear();
        descriptionArea.clear();
        statusComboBox.setValue(null);
        projectsTable.getSelectionModel().clearSelection();
    }

    /**
     * Shows an error message.
     */
    private void showError(String message) {
        messageLabel.setStyle("-fx-text-fill: red;");
        messageLabel.setText(message);
    }

    /**
     * Shows a success message.
     */
    private void showSuccess(String message) {
        messageLabel.setStyle("-fx-text-fill: green;");
        messageLabel.setText(message);
    }

    /**
     * Shows a confirmation dialog.
     */
    private Optional<ButtonType> showConfirmation(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait();
    }
}
