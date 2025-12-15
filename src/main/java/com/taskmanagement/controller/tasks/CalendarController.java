package com.taskmanagement.controller.tasks;

import com.taskmanagement.model.Task;
import com.taskmanagement.model.Employee;
import com.taskmanagement.util.FileManager;
import com.taskmanagement.util.SessionManager;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.fxml.FXML;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for displaying tasks on a calendar view.
 */
public class CalendarController {

    @FXML
    private VBox calendarContainer;

    @FXML
    private Label monthYearLabel;

    @FXML
    private Button previousMonthButton;

    @FXML
    private Button nextMonthButton;

    @FXML
    private GridPane calendarGrid;

    @FXML
    private Label messageLabel;

    private YearMonth currentMonth;
    private List<Task> allTasks;

    @FXML
    public void initialize() {
        currentMonth = YearMonth.now();
        allTasks = FileManager.loadTasks();
        setupButtonActions();
        displayCalendar();
    }

    /**
     * Sets up button actions.
     */
    private void setupButtonActions() {
        previousMonthButton.setOnAction(e -> previousMonth());
        nextMonthButton.setOnAction(e -> nextMonth());
    }

    /**
     * Displays the calendar for the current month.
     */
    private void displayCalendar() {
        monthYearLabel.setText(currentMonth.getMonth() + " " + currentMonth.getYear());
        
        calendarGrid.getChildren().clear();
        
        // Add day headers
        String[] dayHeaders = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (int i = 0; i < 7; i++) {
            Label header = new Label(dayHeaders[i]);
            header.setStyle("-fx-font-weight: bold; -fx-text-alignment: center;");
            calendarGrid.add(header, i, 0);
        }

        // Get first day of month and number of days
        LocalDate firstDay = currentMonth.atDay(1);
        int firstDayOfWeek = firstDay.getDayOfWeek().getValue() % 7;
        int daysInMonth = currentMonth.lengthOfMonth();

        // Add day cells
        int row = 1;
        int col = firstDayOfWeek;

        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = LocalDate.of(currentMonth.getYear(), currentMonth.getMonth(), day);
            VBox dayCell = createDayCell(date);
            calendarGrid.add(dayCell, col, row);

            col++;
            if (col == 7) {
                col = 0;
                row++;
            }
        }
    }

    /**
     * Creates a day cell for the calendar.
     */
    private VBox createDayCell(LocalDate date) {
        VBox cell = new VBox();
        cell.setStyle("-fx-border: 1px solid #cccccc; -fx-padding: 5;");
        cell.setPrefHeight(100);
        cell.setAlignment(Pos.TOP_LEFT);

        // Day number
        Label dayLabel = new Label(String.valueOf(date.getDayOfMonth()));
        dayLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12;");
        cell.getChildren().add(dayLabel);

        // Get tasks for this date
        List<Task> tasksForDate = getTasksForDate(date);
        
        for (Task task : tasksForDate) {
            Label taskLabel = new Label(task.getCode() + ": " + task.getTitle());
            taskLabel.setStyle("-fx-font-size: 10; -fx-text-fill: #0066cc; -fx-wrap-text: true;");
            taskLabel.setWrapText(true);
            cell.getChildren().add(taskLabel);
        }

        return cell;
    }

    /**
     * Gets tasks for a specific date.
     */
    private List<Task> getTasksForDate(LocalDate date) {
        List<Task> tasksForDate = new ArrayList<>();
        Employee currentEmployee = SessionManager.getInstance().getCurrentEmployee();

        for (Task task : allTasks) {
            LocalDate taskDate = task.getStartDate().toLocalDate();
            
            // Check if task starts on this date or is within the date range
            if (taskDate.equals(date) || 
                (taskDate.isBefore(date) && task.getEndDate().toLocalDate().isAfter(date))) {
                
                // Filter by employee if not admin
                if (currentEmployee == null || 
                    task.getAssignedEmployeeId().equals(currentEmployee.getId())) {
                    tasksForDate.add(task);
                }
            }
        }

        return tasksForDate;
    }

    /**
     * Navigates to the previous month.
     */
    @FXML
    private void previousMonth() {
        currentMonth = currentMonth.minusMonths(1);
        displayCalendar();
    }

    /**
     * Navigates to the next month.
     */
    @FXML
    private void nextMonth() {
        currentMonth = currentMonth.plusMonths(1);
        displayCalendar();
    }
}
