package com.taskmanagement.controller.employee;

import com.taskmanagement.model.Timecard;
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
 * Controller for managing timecards (attendance tracking).
 */
public class TimecardController {

    @FXML
    private TableView<Timecard> timecardsTable;

    @FXML
    private TableColumn<Timecard, String> dateColumn;

    @FXML
    private TableColumn<Timecard, String> checkInColumn;

    @FXML
    private TableColumn<Timecard, String> checkOutColumn;

    @FXML
    private TableColumn<Timecard, String> statusColumn;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Spinner<Integer> checkInHourSpinner;

    @FXML
    private Spinner<Integer> checkInMinuteSpinner;

    @FXML
    private Spinner<Integer> checkOutHourSpinner;

    @FXML
    private Spinner<Integer> checkOutMinuteSpinner;

    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    private TextArea notesArea;

    @FXML
    private Button addTimecardButton;

    @FXML
    private Label messageLabel;

    private ObservableList<Timecard> timecardsList;

    @FXML
    public void initialize() {
        setupTableColumns();
        loadTimecards();
        setupSpinners();
        setupComboBoxes();
        setupButtonActions();
    }

    /**
     * Sets up the table columns.
     */
    private void setupTableColumns() {
        dateColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
            cellData.getValue().getArrivalTime().toLocalDate().toString()
        ));
        checkInColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
            cellData.getValue().getArrivalTime() != null ? cellData.getValue().getArrivalTime().toString() : "N/A"
        ));
        checkOutColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
            cellData.getValue().getDepartureTime() != null ? cellData.getValue().getDepartureTime().toString() : "N/A"
        ));
        statusColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
            "PRESENT"
        ));
    }

    /**
     * Loads timecards from the file system.
     */
    private void loadTimecards() {
        Employee currentEmployee = SessionManager.getInstance().getCurrentEmployee();
        List<Timecard> allTimecards = FileManager.loadTimecards();
        
        List<Timecard> employeeTimecards = new java.util.ArrayList<>();
        if (currentEmployee != null) {
            for (Timecard tc : allTimecards) {
                if (tc.getEmployeeId().equals(currentEmployee.getId())) {
                    employeeTimecards.add(tc);
                }
            }
        }
        
        timecardsList = FXCollections.observableArrayList(employeeTimecards);
        timecardsTable.setItems(timecardsList);
    }

    /**
     * Sets up the time spinners.
     */
    private void setupSpinners() {
        SpinnerValueFactory<Integer> checkInHourFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 9);
        SpinnerValueFactory<Integer> checkInMinuteFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
        SpinnerValueFactory<Integer> checkOutHourFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 17);
        SpinnerValueFactory<Integer> checkOutMinuteFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);

        checkInHourSpinner.setValueFactory(checkInHourFactory);
        checkInMinuteSpinner.setValueFactory(checkInMinuteFactory);
        checkOutHourSpinner.setValueFactory(checkOutHourFactory);
        checkOutMinuteSpinner.setValueFactory(checkOutMinuteFactory);
    }

    /**
     * Sets up the status combo box.
     */
    private void setupComboBoxes() {
        statusComboBox.setItems(FXCollections.observableArrayList(
            "PRESENT", "ABSENT", "LATE", "EARLY_LEAVE"
        ));
        statusComboBox.setValue("PRESENT");
    }

    /**
     * Sets up button actions.
     */
    private void setupButtonActions() {
        addTimecardButton.setOnAction(e -> addTimecard());
    }

    /**
     * Adds a new timecard.
     */
    @FXML
    private void addTimecard() {
        if (!validateForm()) {
            return;
        }

        Employee currentEmployee = SessionManager.getInstance().getCurrentEmployee();
        if (currentEmployee == null) {
            showError("No employee logged in!");
            return;
        }

        LocalDate date = datePicker.getValue();
        int checkInHour = checkInHourSpinner.getValue();
        int checkInMinute = checkInMinuteSpinner.getValue();
        int checkOutHour = checkOutHourSpinner.getValue();
        int checkOutMinute = checkOutMinuteSpinner.getValue();
        String status = statusComboBox.getValue();
        String notes = notesArea.getText().trim();

        java.time.LocalDateTime checkInDateTime = java.time.LocalDateTime.of(date, LocalTime.of(checkInHour, checkInMinute));
        java.time.LocalDateTime checkOutDateTime = java.time.LocalDateTime.of(date, LocalTime.of(checkOutHour, checkOutMinute));

        Timecard newTimecard = new Timecard(
            UUID.randomUUID().toString(),
            currentEmployee.getId(),
            checkInDateTime,
            checkOutDateTime,
            notes
        );

        List<Timecard> timecards = FileManager.loadTimecards();
        timecards.add(newTimecard);
        FileManager.saveTimecards(timecards);

        timecardsList.add(newTimecard);
        showSuccess("Timecard added successfully!");
        clearForm();
    }

    /**
     * Validates the form fields.
     */
    private boolean validateForm() {
        if (datePicker.getValue() == null) {
            showError("Please select a date!");
            return false;
        }
        return true;
    }

    /**
     * Clears the form fields.
     */
    private void clearForm() {
        datePicker.setValue(null);
        checkInHourSpinner.getValueFactory().setValue(9);
        checkInMinuteSpinner.getValueFactory().setValue(0);
        checkOutHourSpinner.getValueFactory().setValue(17);
        checkOutMinuteSpinner.getValueFactory().setValue(0);
        statusComboBox.setValue("PRESENT");
        notesArea.clear();
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
