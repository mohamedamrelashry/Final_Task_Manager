package com.taskmanagement.controller.tasks;

import com.taskmanagement.model.TaskLog;
import com.taskmanagement.model.Task;
import com.taskmanagement.model.Employee;
import com.taskmanagement.util.FileManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Controller for managing task logs (time tracking).
 */
public class TaskLogController {

    @FXML
    private TableView<TaskLog> taskLogsTable;

    @FXML
    private TableColumn<TaskLog, String> taskColumn;

    @FXML
    private TableColumn<TaskLog, String> employeeColumn;

    @FXML
    private TableColumn<TaskLog, String> fromTimeColumn;

    @FXML
    private TableColumn<TaskLog, String> toTimeColumn;

    @FXML
    private TableColumn<TaskLog, String> durationColumn;

    @FXML
    private ComboBox<String> taskComboBox;

    @FXML
    private ComboBox<String> employeeComboBox;

    @FXML
    private DatePicker logDatePicker;

    @FXML
    private Spinner<Integer> fromHourSpinner;

    @FXML
    private Spinner<Integer> fromMinuteSpinner;

    @FXML
    private Spinner<Integer> toHourSpinner;

    @FXML
    private Spinner<Integer> toMinuteSpinner;

    @FXML
    private Button addLogButton;

    @FXML
    private Label messageLabel;

    private ObservableList<TaskLog> taskLogsList;

    @FXML
    public void initialize() {
        setupTableColumns();
        loadTaskLogs();
        setupComboBoxes();
        setupSpinners();
        setupButtonActions();
    }

    /**
     * Sets up the table columns.
     */
    private void setupTableColumns() {
        taskColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getTaskCode()));
        employeeColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEmployeeId()));
        fromTimeColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getFromTime().toString()));
        toTimeColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getToTime().toString()));
        durationColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
            String.format("%.2f hours", cellData.getValue().getHoursSpent())
        ));
    }

    /**
     * Loads task logs from the file system.
     */
    private void loadTaskLogs() {
        List<TaskLog> logs = FileManager.loadTaskLogs();
        taskLogsList = FXCollections.observableArrayList(logs);
        taskLogsTable.setItems(taskLogsList);
    }

    /**
     * Sets up the combo boxes.
     */
    private void setupComboBoxes() {
        // Load tasks
        List<Task> tasks = FileManager.loadTasks();
        List<String> taskCodes = new java.util.ArrayList<>();
        for (Task task : tasks) {
            taskCodes.add(task.getCode() + " - " + task.getTitle());
        }
        taskComboBox.setItems(FXCollections.observableArrayList(taskCodes));

        // Load employees
        List<Employee> employees = FileManager.loadEmployees();
        List<String> employeeNames = new java.util.ArrayList<>();
        for (Employee emp : employees) {
            if (emp.isActive()) {
                employeeNames.add(emp.getName());
            }
        }
        employeeComboBox.setItems(FXCollections.observableArrayList(employeeNames));
    }

    /**
     * Sets up the time spinners.
     */
    private void setupSpinners() {
        SpinnerValueFactory<Integer> fromHourFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 9);
        SpinnerValueFactory<Integer> fromMinuteFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        SpinnerValueFactory<Integer> toHourFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 17);
        SpinnerValueFactory<Integer> toMinuteFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);

        fromHourSpinner.setValueFactory(fromHourFactory);
        fromMinuteSpinner.setValueFactory(fromMinuteFactory);
        toHourSpinner.setValueFactory(toHourFactory);
        toMinuteSpinner.setValueFactory(toMinuteFactory);
    }

    /**
     * Sets up button actions.
     */
    private void setupButtonActions() {
        addLogButton.setOnAction(e -> addTaskLog());
    }

    /**
     * Adds a new task log.
     */
    @FXML
    private void addTaskLog() {
        if (!validateForm()) {
            return;
        }

        String taskCode = taskComboBox.getValue().split(" - ")[0].trim();
        String employeeName = employeeComboBox.getValue();
        java.time.LocalDate logDate = logDatePicker.getValue();

        int fromHour = fromHourSpinner.getValue();
        int fromMinute = fromMinuteSpinner.getValue();
        int toHour = toHourSpinner.getValue();
        int toMinute = toMinuteSpinner.getValue();

        LocalDateTime fromTime = LocalDateTime.of(logDate, java.time.LocalTime.of(fromHour, fromMinute));
        LocalDateTime toTime = LocalDateTime.of(logDate, java.time.LocalTime.of(toHour, toMinute));

        // Get employee ID from name
        String employeeId = null;
        List<Employee> employees = FileManager.loadEmployees();
        for (Employee emp : employees) {
            if (emp.getName().equals(employeeName)) {
                employeeId = emp.getId();
                break;
            }
        }

        TaskLog newLog = new TaskLog(
            UUID.randomUUID().toString(),
            taskCode,
            employeeId,
            fromTime,
            toTime,
            ""
        );

        List<TaskLog> logs = FileManager.loadTaskLogs();
        logs.add(newLog);
        FileManager.saveTaskLogs(logs);

        taskLogsList.add(newLog);
        showSuccess("Task log added successfully!");
        clearForm();
    }

    /**
     * Validates the form fields.
     */
    private boolean validateForm() {
        if (taskComboBox.getValue() == null) {
            showError("Please select a task!");
            return false;
        }
        if (employeeComboBox.getValue() == null) {
            showError("Please select an employee!");
            return false;
        }
        if (logDatePicker.getValue() == null) {
            showError("Please select a date!");
            return false;
        }
        return true;
    }

    /**
     * Clears the form fields.
     */
    private void clearForm() {
        taskComboBox.setValue(null);
        employeeComboBox.setValue(null);
        logDatePicker.setValue(null);
        fromHourSpinner.getValueFactory().setValue(9);
        fromMinuteSpinner.getValueFactory().setValue(0);
        toHourSpinner.getValueFactory().setValue(17);
        toMinuteSpinner.getValueFactory().setValue(0);
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
