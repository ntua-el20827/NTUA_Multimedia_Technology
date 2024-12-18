package com.taskmanager.ui;

import com.taskmanager.model.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.UUID;

// TaskController.java
public class TaskController {
    @FXML private TextField titleField;
    @FXML private TextArea descriptionField;
    @FXML private ComboBox<String> categoryCombo;
    @FXML private ComboBox<String> priorityCombo;
    @FXML private ComboBox<String> statusCombo;
    //@FXML private ComboBox<LocalDate> dueDateCombo;
    @FXML private DatePicker dueDatePicker;

    private Task createdTask;
    // Πρεπει να αλλάξει σε currentTask!!

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
        LocalDate dueDate = dueDatePicker.getValue();

        // Check if the title is in the list of titles
        List<UUID> ids = MainController.getIds();
        if (ids.contains(title)) {
            System.out.println("Title already exists!");
            return;
        }


        if (title != null && category != null && priority != null && dueDate != null) {

            createdTask = new Task(title, description, category, priority, dueDate,"Open");
            // Κλείσιμο του παραθύρου
            ((Stage) titleField.getScene().getWindow()).close();
        }
    }

    @FXML
    private void onDeleteTask(Task task) {

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
            //dueDateCombo.setValue(task.getDueDate()); // Δεν το αναγνωρίζει
            dueDatePicker.setValue(task.getDueDate());
        }
    }
}
