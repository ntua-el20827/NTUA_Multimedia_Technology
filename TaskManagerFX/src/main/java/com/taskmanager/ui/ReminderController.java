package com.taskmanager.ui;

import com.taskmanager.model.Reminder;
import com.taskmanager.model.ReminderType;
import com.taskmanager.model.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;

public class ReminderController {
    @FXML
    private ComboBox<ReminderType> typeCombo;
    @FXML
    private DatePicker customDatePicker;
    @FXML
    private Button saveButton;
    @FXML
    private Button deleteButton;

    private Reminder currentReminder;
    private Task task;

    public void setTask(Task task) {
        this.task = task;
    }

    public void setReminder(Reminder reminder) {
        this.currentReminder = reminder;
        typeCombo.setValue(reminder.getType());
        customDatePicker.setValue(reminder.getReminderDate());
        deleteButton.setVisible(true);
    }

    @FXML
    private void initialize() {
        typeCombo.getItems().addAll(ReminderType.values());
        typeCombo.valueProperty().addListener((obs, oldType, newType) -> {
            if (newType == ReminderType.CUSTOM_DATE) {
                customDatePicker.setVisible(true);
            } else {
                customDatePicker.setVisible(false);
            }
        });
        deleteButton.setVisible(false);
    }

    @FXML
    private void onSaveReminder() {
        ReminderType type = typeCombo.getValue();
        LocalDate customDate = customDatePicker.getValue();

        if (type == ReminderType.CUSTOM_DATE && customDate == null) {
            showAlert("Error", "Please select a custom date.");
            return;
        }

        LocalDate reminderDate = calculateReminderDate(type, customDate);

        if (reminderDate == null || reminderDate.isBefore(LocalDate.now())) {
            showAlert("Error", "Invalid reminder date.");
            return;
        }

        if (currentReminder == null) { // New Reminder
            currentReminder = new Reminder(task.getId(),type, reminderDate);
            currentReminder.setTaskTitle(task.getTitle());
            task.addReminder(currentReminder);
        } else { // Update Reminder
            currentReminder.setType(type);
            currentReminder.setReminderDate(reminderDate);
        }

        closeWindow();
    }

    @FXML
    private void onDeleteReminder() {
        if (currentReminder != null) {
            task.removeReminder(currentReminder);
            // Delete reminder // Main Controller will remove it from the list after it catches the null value
            currentReminder = null;
            // Print message
            System.out.println("Reminder deleted.");
            closeWindow();
        }
    }

    private LocalDate calculateReminderDate(ReminderType type, LocalDate customDate) {
        if (task.getDueDate() == null) return null;

        switch (type) {
            case ONE_DAY_BEFORE:
                return task.getDueDate().minusDays(1);
            case ONE_WEEK_BEFORE:
                return task.getDueDate().minusWeeks(1);
            case ONE_MONTH_BEFORE:
                return task.getDueDate().minusMonths(1);
            case CUSTOM_DATE:
                return customDate;
            default:
                return null;
        }
    }

    private void closeWindow() {
        Stage stage = (Stage) saveButton.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public Reminder getReminder() {
        return currentReminder;
    }
}
