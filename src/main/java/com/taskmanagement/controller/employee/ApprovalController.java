package com.taskmanagement.controller.employee;

import com.taskmanagement.model.LeaveRequest;
import com.taskmanagement.model.MissionRequest;
import com.taskmanagement.util.FileManager;
import com.taskmanagement.util.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.util.List;

/**
 * Controller for approving/rejecting leave and mission requests.
 */
public class ApprovalController {

    @FXML
    private TabPane requestTabPane;

    @FXML
    private Tab leaveRequestsTab;

    @FXML
    private Tab missionRequestsTab;

    // Leave Requests Tab
    @FXML
    private TableView<LeaveRequest> leaveRequestsTable;

    @FXML
    private TableColumn<LeaveRequest, String> leaveEmployeeColumn;

    @FXML
    private TableColumn<LeaveRequest, String> leaveTypeColumn;

    @FXML
    private TableColumn<LeaveRequest, String> leaveStartColumn;

    @FXML
    private TableColumn<LeaveRequest, String> leaveEndColumn;

    @FXML
    private TableColumn<LeaveRequest, String> leaveStatusColumn;

    @FXML
    private TextArea leaveNotesArea;

    @FXML
    private Button approveLeaveButton;

    @FXML
    private Button rejectLeaveButton;

    // Mission Requests Tab
    @FXML
    private TableView<MissionRequest> missionRequestsTable;

    @FXML
    private TableColumn<MissionRequest, String> missionEmployeeColumn;

    @FXML
    private TableColumn<MissionRequest, String> missionTypeColumn;

    @FXML
    private TableColumn<MissionRequest, String> missionDateColumn;

    @FXML
    private TableColumn<MissionRequest, String> missionStatusColumn;

    @FXML
    private TextArea missionNotesArea;

    @FXML
    private Button approveMissionButton;

    @FXML
    private Button rejectMissionButton;

    @FXML
    private Label messageLabel;

    private ObservableList<LeaveRequest> leaveRequestsList;
    private ObservableList<MissionRequest> missionRequestsList;

    @FXML
    public void initialize() {
        setupLeaveRequestsTable();
        setupMissionRequestsTable();
        loadRequests();
        setupButtonActions();
    }

    /**
     * Sets up the leave requests table columns.
     */
    private void setupLeaveRequestsTable() {
        leaveEmployeeColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
            cellData.getValue().getEmployeeId()
        ));
        leaveTypeColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
            cellData.getValue().getLeaveType()
        ));
        leaveStartColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
            cellData.getValue().getStartDate().toString()
        ));
        leaveEndColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
            cellData.getValue().getEndDate().toString()
        ));
        leaveStatusColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
            cellData.getValue().getStatus()
        ));
    }

    /**
     * Sets up the mission requests table columns.
     */
    private void setupMissionRequestsTable() {
        missionEmployeeColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
            cellData.getValue().getEmployeeId()
        ));
        missionTypeColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
            cellData.getValue().getType()
        ));
        missionDateColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
            cellData.getValue().getStartTime().toLocalDate().toString()
        ));
        missionStatusColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
            cellData.getValue().getStatus()
        ));
    }

    /**
     * Loads pending requests from the file system.
     */
    private void loadRequests() {
        // Load leave requests
        List<LeaveRequest> allLeaveRequests = FileManager.loadLeaveRequests();
        List<LeaveRequest> pendingLeaveRequests = new java.util.ArrayList<>();
        for (LeaveRequest lr : allLeaveRequests) {
            if ("PENDING".equals(lr.getStatus())) {
                pendingLeaveRequests.add(lr);
            }
        }
        leaveRequestsList = FXCollections.observableArrayList(pendingLeaveRequests);
        leaveRequestsTable.setItems(leaveRequestsList);

        // Load mission requests
        List<MissionRequest> allMissionRequests = FileManager.loadMissionRequests();
        List<MissionRequest> pendingMissionRequests = new java.util.ArrayList<>();
        for (MissionRequest mr : allMissionRequests) {
            if ("PENDING".equals(mr.getStatus())) {
                pendingMissionRequests.add(mr);
            }
        }
        missionRequestsList = FXCollections.observableArrayList(pendingMissionRequests);
        missionRequestsTable.setItems(missionRequestsList);
    }

    /**
     * Sets up button actions.
     */
    private void setupButtonActions() {
        approveLeaveButton.setOnAction(e -> approveLeaveRequest());
        rejectLeaveButton.setOnAction(e -> rejectLeaveRequest());
        approveMissionButton.setOnAction(e -> approveMissionRequest());
        rejectMissionButton.setOnAction(e -> rejectMissionRequest());
    }

    /**
     * Approves a leave request.
     */
    @FXML
    private void approveLeaveRequest() {
        LeaveRequest selected = leaveRequestsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Please select a leave request!");
            return;
        }

        selected.setStatus("APPROVED");
        selected.setReviewedBy(SessionManager.getInstance().getCurrentUser().getUsername());

        List<LeaveRequest> requests = FileManager.loadLeaveRequests();
        for (int i = 0; i < requests.size(); i++) {
            if (requests.get(i).getId().equals(selected.getId())) {
                requests.set(i, selected);
                break;
            }
        }
        FileManager.saveLeaveRequests(requests);

        leaveRequestsList.remove(selected);
        showSuccess("Leave request approved!");
        leaveNotesArea.clear();
    }

    /**
     * Rejects a leave request.
     */
    @FXML
    private void rejectLeaveRequest() {
        LeaveRequest selected = leaveRequestsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Please select a leave request!");
            return;
        }

        String notes = leaveNotesArea.getText().trim();
        if (notes.isEmpty()) {
            showError("Please provide rejection notes!");
            return;
        }

        selected.setStatus("REJECTED");
        selected.setReviewedBy(SessionManager.getInstance().getCurrentUser().getUsername());
        selected.setReviewNotes(notes);

        List<LeaveRequest> requests = FileManager.loadLeaveRequests();
        for (int i = 0; i < requests.size(); i++) {
            if (requests.get(i).getId().equals(selected.getId())) {
                requests.set(i, selected);
                break;
            }
        }
        FileManager.saveLeaveRequests(requests);

        leaveRequestsList.remove(selected);
        showSuccess("Leave request rejected!");
        leaveNotesArea.clear();
    }

    /**
     * Approves a mission request.
     */
    @FXML
    private void approveMissionRequest() {
        MissionRequest selected = missionRequestsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Please select a mission request!");
            return;
        }

        selected.setStatus("APPROVED");
        selected.setReviewedBy(SessionManager.getInstance().getCurrentUser().getUsername());

        List<MissionRequest> requests = FileManager.loadMissionRequests();
        for (int i = 0; i < requests.size(); i++) {
            if (requests.get(i).getId().equals(selected.getId())) {
                requests.set(i, selected);
                break;
            }
        }
        FileManager.saveMissionRequests(requests);

        missionRequestsList.remove(selected);
        showSuccess("Mission request approved!");
        missionNotesArea.clear();
    }

    /**
     * Rejects a mission request.
     */
    @FXML
    private void rejectMissionRequest() {
        MissionRequest selected = missionRequestsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showError("Please select a mission request!");
            return;
        }

        String notes = missionNotesArea.getText().trim();
        if (notes.isEmpty()) {
            showError("Please provide rejection notes!");
            return;
        }

        selected.setStatus("REJECTED");
        selected.setReviewedBy(SessionManager.getInstance().getCurrentUser().getUsername());
        selected.setReviewNotes(notes);

        List<MissionRequest> requests = FileManager.loadMissionRequests();
        for (int i = 0; i < requests.size(); i++) {
            if (requests.get(i).getId().equals(selected.getId())) {
                requests.set(i, selected);
                break;
            }
        }
        FileManager.saveMissionRequests(requests);

        missionRequestsList.remove(selected);
        showSuccess("Mission request rejected!");
        missionNotesArea.clear();
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
