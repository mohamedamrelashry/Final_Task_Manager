package com.taskmanagement.controller.admin;

import com.taskmanagement.model.Employee;
import com.taskmanagement.model.User;
import com.taskmanagement.util.FileManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Controller for managing employees (Add, Update, Delete).
 */
public class EmployeeManagementController {

    @FXML
    private TableView<Employee> employeesTable;

    @FXML
    private TableColumn<Employee, String> nameColumn;

    @FXML
    private TableColumn<Employee, String> emailColumn;

    @FXML
    private TableColumn<Employee, String> typeColumn;

    @FXML
    private TableColumn<Employee, String> departmentColumn;

    @FXML
    private TextField nameField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private TextField departmentField;

    @FXML
    private TextField salaryField;

    @FXML
    private ComboBox<String> userComboBox;

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

    private ObservableList<Employee> employeesList;
    private List<User> usersList;

    @FXML
    public void initialize() {
        setupTableColumns();
        loadEmployees();
        setupComboBoxes();
        setupButtonActions();
    }

    /**
     * Sets up the table columns.
     */
    private void setupTableColumns() {
        nameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getName()));
        emailColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEmail()));
        typeColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEmployeeType()));
        departmentColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDepartment()));

        employeesTable.setOnMouseClicked(e -> loadSelectedEmployee());
    }

    /**
     * Loads employees from the file system.
     */
    private void loadEmployees() {
        List<Employee> employees = FileManager.loadEmployees();
        employeesList = FXCollections.observableArrayList(employees);
        employeesTable.setItems(employeesList);
    }

    /**
     * Sets up the combo boxes.
     */
    private void setupComboBoxes() {
        typeComboBox.setItems(FXCollections.observableArrayList("Developer", "Designer", "QA", "Manager", "Analyst"));
        
        usersList = FileManager.loadUsers();
        ObservableList<String> usernames = FXCollections.observableArrayList();
        usernames.add("(No User)");
        for (User user : usersList) {
            usernames.add(user.getUsername());
        }
        userComboBox.setItems(usernames);
    }

    /**
     * Sets up button actions.
     */
    private void setupButtonActions() {
        addButton.setOnAction(e -> addEmployee());
        updateButton.setOnAction(e -> updateEmployee());
        deleteButton.setOnAction(e -> deleteEmployee());
    }

    /**
     * Loads the selected employee into the form fields.
     */
    private void loadSelectedEmployee() {
        Employee selectedEmployee = employeesTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            nameField.setText(selectedEmployee.getName());
            emailField.setText(selectedEmployee.getEmail());
            phoneField.setText(selectedEmployee.getPhone());
            typeComboBox.setValue(selectedEmployee.getEmployeeType());
            departmentField.setText(selectedEmployee.getDepartment());
            salaryField.setText(String.valueOf(selectedEmployee.getSalary()));
            activeCheckBox.setSelected(selectedEmployee.isActive());

            // Set user combo box
            if (selectedEmployee.getUserId() != null) {
                for (User user : usersList) {
                    if (user.getId().equals(selectedEmployee.getUserId())) {
                        userComboBox.setValue(user.getUsername());
                        break;
                    }
                }
            } else {
                userComboBox.setValue("(No User)");
            }
        }
    }

    /**
     * Adds a new employee.
     */
    @FXML
    private void addEmployee() {
        if (!validateForm()) {
            return;
        }

        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String phone = phoneField.getText().trim();
        String type = typeComboBox.getValue();
        String department = departmentField.getText().trim();
        double salary = Double.parseDouble(salaryField.getText().trim());

        // Get user ID
        String userId = null;
        String selectedUsername = userComboBox.getValue();
        if (!selectedUsername.equals("(No User)")) {
            for (User user : usersList) {
                if (user.getUsername().equals(selectedUsername)) {
                    userId = user.getId();
                    break;
                }
            }
        }

        Employee newEmployee = new Employee(
            UUID.randomUUID().toString(),
            name,
            email,
            phone,
            type,
            userId,
            department,
            salary
        );

        employeesList.add(newEmployee);
        FileManager.saveEmployees(new java.util.ArrayList<>(employeesList));
        clearForm();
        showSuccess("Employee added successfully!");
    }

    /**
     * Updates the selected employee.
     */
    @FXML
    private void updateEmployee() {
        Employee selectedEmployee = employeesTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee == null) {
            showError("Please select an employee to update!");
            return;
        }

        if (!validateForm()) {
            return;
        }

        selectedEmployee.setName(nameField.getText().trim());
        selectedEmployee.setEmail(emailField.getText().trim());
        selectedEmployee.setPhone(phoneField.getText().trim());
        selectedEmployee.setEmployeeType(typeComboBox.getValue());
        selectedEmployee.setDepartment(departmentField.getText().trim());
        selectedEmployee.setSalary(Double.parseDouble(salaryField.getText().trim()));
        selectedEmployee.setActive(activeCheckBox.isSelected());

        // Set user ID
        String selectedUsername = userComboBox.getValue();
        if (!selectedUsername.equals("(No User)")) {
            for (User user : usersList) {
                if (user.getUsername().equals(selectedUsername)) {
                    selectedEmployee.setUserId(user.getId());
                    break;
                }
            }
        } else {
            selectedEmployee.setUserId(null);
        }

        FileManager.saveEmployees(new java.util.ArrayList<>(employeesList));
        employeesTable.refresh();
        showSuccess("Employee updated successfully!");
    }

    /**
     * Deletes the selected employee.
     */
    @FXML
    private void deleteEmployee() {
        Employee selectedEmployee = employeesTable.getSelectionModel().getSelectedItem();
        if (selectedEmployee == null) {
            showError("Please select an employee to delete!");
            return;
        }

        Optional<ButtonType> result = showConfirmation("Are you sure you want to delete this employee?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            employeesList.remove(selectedEmployee);
            FileManager.saveEmployees(new java.util.ArrayList<>(employeesList));
            clearForm();
            showSuccess("Employee deleted successfully!");
        }
    }

    /**
     * Validates the form fields.
     */
    private boolean validateForm() {
        if (nameField.getText().trim().isEmpty()) {
            showError("Name cannot be empty!");
            return false;
        }
        if (emailField.getText().trim().isEmpty()) {
            showError("Email cannot be empty!");
            return false;
        }
        if (typeComboBox.getValue() == null) {
            showError("Please select an employee type!");
            return false;
        }
        if (departmentField.getText().trim().isEmpty()) {
            showError("Department cannot be empty!");
            return false;
        }
        try {
            Double.parseDouble(salaryField.getText().trim());
        } catch (NumberFormatException e) {
            showError("Salary must be a valid number!");
            return false;
        }
        return true;
    }

    /**
     * Clears the form fields.
     */
    private void clearForm() {
        nameField.clear();
        emailField.clear();
        phoneField.clear();
        typeComboBox.setValue(null);
        departmentField.clear();
        salaryField.clear();
        userComboBox.setValue("(No User)");
        activeCheckBox.setSelected(true);
        employeesTable.getSelectionModel().clearSelection();
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
