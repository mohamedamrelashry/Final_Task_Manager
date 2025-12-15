package com.taskmanagement.controller.admin;

import com.taskmanagement.model.User;
import com.taskmanagement.util.FileManager;
import com.taskmanagement.util.PasswordUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Controller for managing users (Add, Update, Delete).
 */
public class UserManagementController {

    @FXML
    private TableView<User> usersTable;

    @FXML
    private TableColumn<User, String> usernameColumn;

    @FXML
    private TableColumn<User, String> roleColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TableColumn<User, Boolean> activeColumn;

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<String> roleComboBox;

    @FXML
    private TextField emailField;

    @FXML
    private CheckBox activeCheckBox;

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Label messageLabel;

    private ObservableList<User> usersList;

    @FXML
    public void initialize() {
        setupTableColumns();
        loadUsers();
        setupRoleComboBox();
        setupButtonActions();
    }

    /**
     * Sets up the table columns.
     */
    private void setupTableColumns() {
        usernameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getUsername()));
        roleColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getRole()));
        emailColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEmail()));
        activeColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleBooleanProperty(cellData.getValue().isActive()));

        usersTable.setOnMouseClicked(e -> loadSelectedUser());
    }

    /**
     * Loads users from the file system.
     */
    private void loadUsers() {
        List<User> users = FileManager.loadUsers();
        usersList = FXCollections.observableArrayList(users);
        usersTable.setItems(usersList);
    }

    /**
     * Sets up the role combo box.
     */
    private void setupRoleComboBox() {
        roleComboBox.setItems(FXCollections.observableArrayList("ADMIN", "LEADER", "EMPLOYEE"));
    }

    /**
     * Sets up button actions.
     */
    private void setupButtonActions() {
        addButton.setOnAction(e -> addUser());
        updateButton.setOnAction(e -> updateUser());
        deleteButton.setOnAction(e -> deleteUser());
    }

    /**
     * Loads the selected user into the form fields.
     */
    private void loadSelectedUser() {
        User selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            usernameField.setText(selectedUser.getUsername());
            passwordField.setText("");
            roleComboBox.setValue(selectedUser.getRole());
            emailField.setText(selectedUser.getEmail());
            activeCheckBox.setSelected(selectedUser.isActive());
        }
    }

    /**
     * Adds a new user.
     */
    @FXML
    private void addUser() {
        if (!validateForm()) {
            return;
        }

        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String role = roleComboBox.getValue();
        String email = emailField.getText().trim();

        // Check if username already exists
        for (User user : usersList) {
            if (user.getUsername().equals(username)) {
                showError("Username already exists!");
                return;
            }
        }

        User newUser = new User(
            UUID.randomUUID().toString(),
            username,
            PasswordUtil.hashPassword(password),
            role,
            email
        );

        usersList.add(newUser);
        FileManager.saveUsers(new java.util.ArrayList<>(usersList));
        clearForm();
        showSuccess("User added successfully!");
    }

    /**
     * Updates the selected user.
     */
    @FXML
    private void updateUser() {
        User selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showError("Please select a user to update!");
            return;
        }

        if (!validateForm()) {
            return;
        }

        String password = passwordField.getText();
        if (!password.isEmpty()) {
            selectedUser.setPassword(PasswordUtil.hashPassword(password));
        }

        selectedUser.setRole(roleComboBox.getValue());
        selectedUser.setEmail(emailField.getText().trim());
        selectedUser.setActive(activeCheckBox.isSelected());

        FileManager.saveUsers(new java.util.ArrayList<>(usersList));
        usersTable.refresh();
        showSuccess("User updated successfully!");
    }

    /**
     * Deletes the selected user.
     */
    @FXML
    private void deleteUser() {
        User selectedUser = usersTable.getSelectionModel().getSelectedItem();
        if (selectedUser == null) {
            showError("Please select a user to delete!");
            return;
        }

        Optional<ButtonType> result = showConfirmation("Are you sure you want to delete this user?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            usersList.remove(selectedUser);
            FileManager.saveUsers(new java.util.ArrayList<>(usersList));
            clearForm();
            showSuccess("User deleted successfully!");
        }
    }

    /**
     * Validates the form fields.
     */
    private boolean validateForm() {
        if (usernameField.getText().trim().isEmpty()) {
            showError("Username cannot be empty!");
            return false;
        }
        if (passwordField.getText().isEmpty()) {
            showError("Password cannot be empty!");
            return false;
        }
        if (roleComboBox.getValue() == null) {
            showError("Please select a role!");
            return false;
        }
        if (emailField.getText().trim().isEmpty()) {
            showError("Email cannot be empty!");
            return false;
        }
        return true;
    }

    /**
     * Clears the form fields.
     */
    private void clearForm() {
        usernameField.clear();
        passwordField.clear();
        roleComboBox.setValue(null);
        emailField.clear();
        activeCheckBox.setSelected(true);
        usersTable.getSelectionModel().clearSelection();
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
