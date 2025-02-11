package com.taskmanager.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import com.taskmanager.model.PriorityLevel;
import javafx.stage.Stage;

public class PriorityController {
    @FXML private Button saveButton;
    @FXML private TextField priorityLevelField;
    @FXML private Button deleteButton;

    private PriorityLevel currentPriority;
    private boolean priorityDeleted = false;

    @FXML
    private void initialize() {
        deleteButton.setVisible(false);
    }

    @FXML
    public void setPriority(PriorityLevel priority) {
        if (priority != null) {
            this.currentPriority = priority;
            priorityLevelField.setText(priority.getLevel());
            // Delete button not shown if priorty is "Default"
            deleteButton.setVisible(!priority.getLevel().equals("Default"));
            // Make default not clickable
            priorityLevelField.setDisable(priority.getLevel().equals("Default"));
            // Delete save button if priority is "Default"
            saveButton.setDisable(priority.getLevel().equals("Default"));
        }
    }

    @FXML
    public PriorityLevel getPriority() {
        return priorityDeleted ? null : currentPriority;
    }

    @FXML
    private void onSavePriority() {
        if (priorityLevelField.getText().isEmpty()) {
            showAlert("Validation Error", "Priority level cannot be empty.");
            return;
        }

        if (currentPriority == null) { // New priority
            currentPriority = new PriorityLevel(priorityLevelField.getText());
        } else { // Edit existing
            // If user tries to change the default priority level, show an error
            if (currentPriority.getLevel().equals("Default")) {
                showAlert("Validation Error", "Cannot change the default priority level.");
                return;
            }
            currentPriority.setLevel(priorityLevelField.getText());
        }
        closeWindow();
    }

    @FXML
    private void onDeletePriority() {
        priorityDeleted = true;
        closeWindow();
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void closeWindow() {
        Stage stage = (Stage) priorityLevelField.getScene().getWindow();
        stage.close();
    }

}
