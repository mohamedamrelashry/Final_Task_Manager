package com.taskmanagement.controller.employee;

import com.taskmanagement.model.LeaveRequest;
import com.taskmanagement.model.Employee;
import com.taskmanagement.util.FileManager;
import com.taskmanagement.util.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Controller for managing leave requests.
 */
public class LeaveRequestController {

    @FXML
    private TableView<LeaveRequest> leaveRequestsTable;

    @FXML
    private TableColumn<LeaveRequest, String> leaveTypeColumn;

    @FXML
    private TableColumn<LeaveRequest, String> startDateColumn;

    @FXML
    private TableColumn<LeaveRequest, String> endDateColumn;

    @FXML
    private TableColumn<LeaveRequest, String> statusColumn;

    @FXML
    private ComboBox<String> leaveTypeComboBox;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TextArea reasonArea;

    @FXML
    private Button submitButton;

    @FXML
    private Label messageLabel;

    private ObservableList<LeaveRequest> leaveRequestsList;

    @FXML
    public void initialize() {
        setupTableColumns();
        loadLeaveRequests();
        setupComboBoxes();
        setupButtonActions();
    }

    /**
     * Sets up the table columns.
     */
    private void setupTableColumns() {
        leaveTypeColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
            cellData.getValue().getLeaveType()
        ));
        startDateColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
            cellData.getValue().getStartDate().toString()
        ));
        endDateColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
            cellData.getValue().getEndDate().toString()
        ));
        statusColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
            cellData.getValue().getStatus()
        ));
    }

    /**
     * Loads leave requests from the file system.
     */
    private void loadLeaveRequests() {
        Employee currentEmployee = SessionManager.getInstance().getCurrentEmployee();
        List<LeaveRequest> allRequests = FileManager.loadLeaveRequests();
        
        List<LeaveRequest> employeeRequests = new java.util.ArrayList<>();
        if (currentEmployee != null) {
            for (LeaveRequest lr : allRequests) {
                if (lr.getEmployeeId().equals(currentEmployee.getId())) {
                    employeeRequests.add(lr);
                }
            }
        }
        
        leaveRequestsList = FXCollections.observableArrayList(employeeRequests);
        leaveRequestsTable.setItems(leaveRequestsList);
    }

    /**
     * Sets up the leave type combo box.
     */
    private void setupComboBoxes() {
        leaveTypeComboBox.setItems(FXCollections.observableArrayList(
            "ANNUAL", "SICK", "PERSONAL", "MATERNITY", "STUDY"
        ));
    }

    /**
     * Sets up button actions.
     */
    private void setupButtonActions() {
        submitButton.setOnAction(e -> submitLeaveRequest());
    }

    /**
     * Submits a new leave request.
     */
    @FXML
    private void submitLeaveRequest() {
        if (!validateForm()) {
            return;
        }

        Employee currentEmployee = SessionManager.getInstance().getCurrentEmployee();
        if (currentEmployee == null) {
            showError("No employee logged in!");
            return;
        }

        String leaveType = leaveTypeComboBox.getValue();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();
        String reason = reasonArea.getText().trim();

        LeaveRequest newRequest = new LeaveRequest(
            UUID.randomUUID().toString(),
            currentEmployee.getId(),
            leaveType,
            startDate,
            endDate,
            reason
        );
        newRequest.setStatus("PENDING");

        List<LeaveRequest> requests = FileManager.loadLeaveRequests();
        requests.add(newRequest);
        FileManager.saveLeaveRequests(requests);

        leaveRequestsList.add(newRequest);
        showSuccess("Leave request submitted successfully!");
        clearForm();
    }

    /**
     * Validates the form fields.
     */
    private boolean validateForm() {
        if (leaveTypeComboBox.getValue() == null) {
            showError("Please select a leave type!");
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
        if (reasonArea.getText().trim().isEmpty()) {
            showError("Please provide a reason!");
            return false;
        }
        if (startDatePicker.getValue().isAfter(endDatePicker.getValue())) {
            showError("Start date must be before end date!");
            return false;
        }
        return true;
    }

    /**
     * Clears the form fields.
     */
    private void clearForm() {
        leaveTypeComboBox.setValue(null);
        startDatePicker.setValue(null);
        endDatePicker.setValue(null);
        reasonArea.clear();
    }

    /**
     * Shows an error message.
     */
    private void showError(String message) {
        if (messageLabel != null) {
            messageLabel.setStyle("-fx-text-fill: red;");
            messageLabel.setText(message);
        }
    }

    /**
     * Shows a success message.
     */
    private void showSuccess(String message) {
        messageLabel.setStyle("-fx-text-fill: green;");
        messageLabel.setText(message);
    }
}
