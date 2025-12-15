package com.taskmanagement.controller.employee;

import com.taskmanagement.model.MissionRequest;
import com.taskmanagement.model.Employee;
import com.taskmanagement.util.FileManager;
import com.taskmanagement.util.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

/**
 * Controller for managing mission and permission requests.
 */
public class MissionRequestController {

    @FXML
    private TableView<MissionRequest> requestsTable;

    @FXML
    private TableColumn<MissionRequest, String> typeColumn;

    @FXML
    private TableColumn<MissionRequest, String> dateColumn;

    @FXML
    private TableColumn<MissionRequest, String> destinationColumn;

    @FXML
    private TableColumn<MissionRequest, String> statusColumn;

    @FXML
    private ComboBox<String> requestTypeComboBox;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Spinner<Integer> startHourSpinner;

    @FXML
    private Spinner<Integer> startMinuteSpinner;

    @FXML
    private Spinner<Integer> endHourSpinner;

    @FXML
    private Spinner<Integer> endMinuteSpinner;

    @FXML
    private TextField destinationField;

    @FXML
    private TextArea purposeArea;

    @FXML
    private Button submitButton;

    @FXML
    private Label messageLabel;

    private ObservableList<MissionRequest> requestsList;

    @FXML
    public void initialize() {
        setupTableColumns();
        loadRequests();
        setupComboBoxes();
        setupSpinners();
        setupButtonActions();
    }

    /**
     * Sets up the table columns.
     */
    private void setupTableColumns() {
        typeColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
            cellData.getValue().getType()
        ));
        dateColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
            cellData.getValue().getStartTime().toLocalDate().toString()
        ));
        destinationColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
            cellData.getValue().getDestination() != null ? cellData.getValue().getDestination() : "N/A"
        ));
        statusColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
            cellData.getValue().getStatus()
        ));
    }

    /**
     * Loads requests from the file system.
     */
    private void loadRequests() {
        Employee currentEmployee = SessionManager.getInstance().getCurrentEmployee();
        List<MissionRequest> allRequests = FileManager.loadMissionRequests();
        
        List<MissionRequest> employeeRequests = new java.util.ArrayList<>();
        if (currentEmployee != null) {
            for (MissionRequest mr : allRequests) {
                if (mr.getEmployeeId().equals(currentEmployee.getId())) {
                    employeeRequests.add(mr);
                }
            }
        }
        
        requestsList = FXCollections.observableArrayList(employeeRequests);
        requestsTable.setItems(requestsList);
    }

    /**
     * Sets up the request type combo box.
     */
    private void setupComboBoxes() {
        requestTypeComboBox.setItems(FXCollections.observableArrayList(
            "MISSION", "PERMISSION"
        ));
    }

    /**
     * Sets up the time spinners.
     */
    private void setupSpinners() {
        SpinnerValueFactory<Integer> startHourFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 9);
        SpinnerValueFactory<Integer> startMinuteFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        SpinnerValueFactory<Integer> endHourFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 17);
        SpinnerValueFactory<Integer> endMinuteFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);

        startHourSpinner.setValueFactory(startHourFactory);
        startMinuteSpinner.setValueFactory(startMinuteFactory);
        endHourSpinner.setValueFactory(endHourFactory);
        endMinuteSpinner.setValueFactory(endMinuteFactory);
    }

    /**
     * Sets up button actions.
     */
    private void setupButtonActions() {
        submitButton.setOnAction(e -> submitRequest());
    }

    /**
     * Submits a new mission/permission request.
     */
    @FXML
    private void submitRequest() {
        if (!validateForm()) {
            return;
        }

        Employee currentEmployee = SessionManager.getInstance().getCurrentEmployee();
        if (currentEmployee == null) {
            showError("No employee logged in!");
            return;
        }

        String type = requestTypeComboBox.getValue();
        LocalDate date = datePicker.getValue();
        int startHour = startHourSpinner.getValue();
        int startMinute = startMinuteSpinner.getValue();
        int endHour = endHourSpinner.getValue();
        int endMinute = endMinuteSpinner.getValue();
        String destination = destinationField.getText().trim();
        String purpose = purposeArea.getText().trim();

        java.time.LocalDateTime startDateTime = java.time.LocalDateTime.of(date, LocalTime.of(startHour, startMinute));
        java.time.LocalDateTime endDateTime = java.time.LocalDateTime.of(date, LocalTime.of(endHour, endMinute));

        MissionRequest newRequest = new MissionRequest(
            UUID.randomUUID().toString(),
            currentEmployee.getId(),
            type,
            startDateTime,
            endDateTime,
            destination,
            purpose
        );

        List<MissionRequest> requests = FileManager.loadMissionRequests();
        requests.add(newRequest);
        FileManager.saveMissionRequests(requests);

        requestsList.add(newRequest);
        showSuccess("Request submitted successfully!");
        clearForm();
    }

    /**
     * Validates the form fields.
     */
    private boolean validateForm() {
        if (requestTypeComboBox.getValue() == null) {
            showError("Please select a request type!");
            return false;
        }
        if (datePicker.getValue() == null) {
            showError("Please select a date!");
            return false;
        }
        if (destinationField.getText().trim().isEmpty()) {
            showError("Please enter a destination!");
            return false;
        }
        if (purposeArea.getText().trim().isEmpty()) {
            showError("Please enter a purpose!");
            return false;
        }
        return true;
    }

    /**
     * Clears the form fields.
     */
    private void clearForm() {
        requestTypeComboBox.setValue(null);
        datePicker.setValue(null);
        startHourSpinner.getValueFactory().setValue(9);
        startMinuteSpinner.getValueFactory().setValue(0);
        endHourSpinner.getValueFactory().setValue(17);
        endMinuteSpinner.getValueFactory().setValue(0);
        destinationField.clear();
        purposeArea.clear();
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
