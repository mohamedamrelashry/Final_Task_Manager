package com.taskmanagement.controller.tasks;

import com.taskmanagement.model.Task;
import com.taskmanagement.model.Employee;
import com.taskmanagement.model.Project;
import com.taskmanagement.util.FileManager;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Controller for creating new tasks.
 */
public class CreateTaskController {

    @FXML
    private TextField codeField;

    @FXML
    private TextField titleField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private ComboBox<String> assignedComboBox;

    @FXML
    private ComboBox<String> phaseComboBox;

    @FXML
    private ComboBox<String> projectComboBox;

    @FXML
    private ComboBox<String> priorityComboBox;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TextField estimationHoursField;

    @FXML
    private Button createButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Label messageLabel;

    @FXML
    public void initialize() {
        setupComboBoxes();
        setupButtonActions();
    }

    /**
     * Sets up the combo boxes.
     */
    private void setupComboBoxes() {
        // Load employees
        List<Employee> employees = FileManager.loadEmployees();
        List<String> employeeNames = new java.util.ArrayList<>();
        for (Employee emp : employees) {
            if (emp.isActive()) {
                employeeNames.add(emp.getName());
            }
        }
        assignedComboBox.setItems(FXCollections.observableArrayList(employeeNames));

        // Task phases
        phaseComboBox.setItems(FXCollections.observableArrayList(
            "Pending", "Under Work", "Test", "Evaluation", "Completed", "Cancelled"
        ));

        // Load projects
        List<Project> projects = FileManager.loadProjects();
        List<String> projectNames = new java.util.ArrayList<>();
        for (Project proj : projects) {
            projectNames.add(proj.getName());
        }
        projectComboBox.setItems(FXCollections.observableArrayList(projectNames));

        // Priority levels
        priorityComboBox.setItems(FXCollections.observableArrayList(
            "Low", "Medium", "High", "Critical"
        ));
    }

    /**
     * Sets up button actions.
     */
    private void setupButtonActions() {
        createButton.setOnAction(e -> createTask());
        cancelButton.setOnAction(e -> cancelCreate());
    }

    /**
     * Creates a new task.
     */
    @FXML
    private void createTask() {
        if (!validateForm()) {
            return;
        }

        String code = codeField.getText().trim();
        String title = titleField.getText().trim();
        String description = descriptionArea.getText().trim();
        String assigned = assignedComboBox.getValue();
        String phase = phaseComboBox.getValue();
        String project = projectComboBox.getValue();
        String priority = priorityComboBox.getValue();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        int estimationHours = Integer.parseInt(estimationHoursField.getText().trim());

        // Get employee ID from name
        String employeeId = null;
        List<Employee> employees = FileManager.loadEmployees();
        for (Employee emp : employees) {
            if (emp.getName().equals(assigned)) {
                employeeId = emp.getId();
                break;
            }
        }

        // Get project ID from name
        String projectId = null;
        List<Project> projects = FileManager.loadProjects();
        for (Project proj : projects) {
            if (proj.getName().equals(project)) {
                projectId = proj.getId();
                break;
            }
        }

        Task newTask = new Task(
            code,
            title,
            description,
            employeeId,
            phase,
            projectId,
            priority,
            null, // creatorId - can be set from session
            java.time.LocalDateTime.of(startDate, java.time.LocalTime.of(9, 0)),
            java.time.LocalDateTime.of(endDate, java.time.LocalTime.of(17, 0)),
            estimationHours
        );

        List<Task> tasks = FileManager.loadTasks();
        tasks.add(newTask);
        FileManager.saveTasks(tasks);

        showSuccess("Task created successfully!");
        clearForm();
    }

    /**
     * Cancels the task creation.
     */
    @FXML
    private void cancelCreate() {
        clearForm();
    }

    /**
     * Validates the form fields.
     */
    private boolean validateForm() {
        if (codeField.getText().trim().isEmpty()) {
            showError("Task code cannot be empty!");
            return false;
        }
        if (titleField.getText().trim().isEmpty()) {
            showError("Task title cannot be empty!");
            return false;
        }
        if (assignedComboBox.getValue() == null) {
            showError("Please select an assigned employee!");
            return false;
        }
        if (phaseComboBox.getValue() == null) {
            showError("Please select a phase!");
            return false;
        }
        if (projectComboBox.getValue() == null) {
            showError("Please select a project!");
            return false;
        }
        if (priorityComboBox.getValue() == null) {
            showError("Please select a priority!");
            return false;
        }
        if (startDatePicker.getValue() == null) {
            showError("Please select a start date!");
            return false;
        }
        if (endDatePicker.getValue() == null) {
            showError("Please select an end date!");
            return false;
        }
        try {
            Integer.parseInt(estimationHoursField.getText().trim());
        } catch (NumberFormatException e) {
            showError("Estimation hours must be a valid number!");
            return false;
        }
        return true;
    }

    /**
     * Clears the form fields.
     */
    private void clearForm() {
        codeField.clear();
        titleField.clear();
        descriptionArea.clear();
        assignedComboBox.setValue(null);
        phaseComboBox.setValue(null);
        projectComboBox.setValue(null);
        priorityComboBox.setValue(null);
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        estimationHoursField.clear();
        messageLabel.setText("");
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
