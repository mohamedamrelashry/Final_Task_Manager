package com.taskmanagement.controller.tasks;

import com.taskmanagement.model.Task;
import com.taskmanagement.model.Employee;
import com.taskmanagement.model.Project;
import com.taskmanagement.util.FileManager;
import com.taskmanagement.util.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for managing all tasks or employee's tasks.
 */
public class TaskManagementController {

    @FXML
    private TableView<Task> tasksTable;

    @FXML
    private TableColumn<Task, String> codeColumn;

    @FXML
    private TableColumn<Task, String> titleColumn;

    @FXML
    private TableColumn<Task, String> assignedColumn;

    @FXML
    private TableColumn<Task, String> phaseColumn;

    @FXML
    private TableColumn<Task, String> projectColumn;

    @FXML
    private TableColumn<Task, String> priorityColumn;

    @FXML
    private Label messageLabel;

    private ObservableList<Task> tasksList;
    private boolean myTasksOnly = false;

    /**
     * Sets the mode to show only my tasks or all tasks.
     */
    public void setMyTasksOnly(boolean myTasksOnly) {
        this.myTasksOnly = myTasksOnly;
    }

    @FXML
    public void initialize() {
        setupTableColumns();
        loadTasks();
    }

    /**
     * Sets up the table columns.
     */
    private void setupTableColumns() {
        codeColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCode()));
        titleColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTitle()));
        assignedColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAssignedEmployeeId()));
        phaseColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTaskPhase()));
        projectColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getProjectId()));
        priorityColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getPriority()));
    }

    /**
     * Loads tasks from the file system.
     */
    private void loadTasks() {
        List<Task> tasks = FileManager.loadTasks();

        // Filter tasks if showing only my tasks
        if (myTasksOnly) {
            Employee currentEmployee = SessionManager.getInstance().getCurrentEmployee();
            if (currentEmployee != null) {
                tasks = tasks.stream()
                    .filter(t -> t.getAssignedEmployeeId().equals(currentEmployee.getId()))
                    .collect(Collectors.toList());
            }
        }

        tasksList = FXCollections.observableArrayList(tasks);
        tasksTable.setItems(tasksList);
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
}
