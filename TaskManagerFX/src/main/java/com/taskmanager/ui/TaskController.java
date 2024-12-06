package com.taskmanager.ui;

import com.taskmanager.model.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.Month;

// TaskController.java
public class TaskController {
    @FXML private TextField titleField;
    @FXML private TextArea descriptionField;
    @FXML private ComboBox<String> categoryCombo;
    @FXML private ComboBox<String> priorityCombo;
    @FXML private ComboBox<String> statusCombo;
    @FXML private ComboBox<LocalDate> dueDateCombo;


    private Task createdTask;

    @FXML
    private void initialize() {
        // Προσθήκη dummy επιλογών για το παράδειγμα
        statusCombo.getItems().addAll("Open", "In Progress", "Postponed", "Completed", "Delayed");
        categoryCombo.getItems().addAll("Work", "Personal", "Hobby");
        priorityCombo.getItems().addAll("Low", "Medium", "High");
    }

    @FXML
    private void onSaveTask() {
        String title = titleField.getText();
        String description = descriptionField.getText();
        String category = categoryCombo.getValue();
        String priority = priorityCombo.getValue();

        if (title != null && category != null && priority != null) {
            LocalDate date = LocalDate.of(2024, Month.DECEMBER, 8);
            createdTask = new Task(title, description, category, priority, date,"Open");
            // Κλείσιμο του παραθύρου
            ((Stage) titleField.getScene().getWindow()).close();
        }
    }

    public Task getCreatedTask() {
        return createdTask;
    }

    public void setTask(Task task) {
        if (task != null) {
            titleField.setText(task.getTitle());
            descriptionField.setText(task.getDescription());
            categoryCombo.setValue(task.getCategory());
            priorityCombo.setValue(task.getPriority());
            statusCombo.setValue(task.getStatus());
            dueDateCombo.setValue(task.getDueDate());
        }
    }
}
