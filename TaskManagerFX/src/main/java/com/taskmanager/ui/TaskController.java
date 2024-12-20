package com.taskmanager.ui;

import com.taskmanager.json.JSONHandler;
import com.taskmanager.model.Category;
import com.taskmanager.model.PriorityLevel;
import com.taskmanager.model.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.util.List;

// TaskController.java
public class TaskController {
    @FXML private TextField titleField;
    @FXML private TextArea descriptionField;
    @FXML private ComboBox<String> categoryCombo;
    @FXML private ComboBox<String> priorityCombo;
    @FXML private ComboBox<String> statusCombo;
    //@FXML private ComboBox<LocalDate> dueDateCombo;
    @FXML private DatePicker dueDatePicker;
    @FXML private Button deleteButton;

    private Task currentTask;
    private List<Category> categories;
    private List<PriorityLevel> priorities;
    private boolean taskDeleted = false; // Flag to track if the task is deleted

    @FXML
    private void initialize() {
        statusCombo.getItems().addAll("Open", "In Progress", "Postponed", "Completed", "Delayed");
        // Προσθήκη dummy επιλογών για το παράδειγμα
        //categoryCombo.getItems().addAll("Work", "Personal", "Hobby");
        //priorityCombo.getItems().addAll("Low", "Medium", "High");

        //
        /*
        if (categories != null) {
            categoryCombo.getItems().setAll(categories.stream().map(Category::getName).toList());
        }
        if (priorities != null) {
            priorityCombo.getItems().setAll(priorities.stream().map(PriorityLevel::getLevel).toList());
        }
        */

        deleteButton.setVisible(false);
    }

    // Take the task from the MainController and set it to the currentTask to edit it
    public void setTask(Task task) {
        if (task != null) {
            this.currentTask = task;
            titleField.setText(task.getTitle());
            descriptionField.setText(task.getDescription());
            categoryCombo.setValue(task.getCategory());
            priorityCombo.setValue(task.getPriority());
            statusCombo.setValue(task.getStatus());
            dueDatePicker.setValue(task.getDueDate());
            // Make the Delete button visible for existing tasks
            deleteButton.setVisible(true);
        }

    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
        if (categories != null) {
            categoryCombo.getItems().setAll(categories.stream().map(Category::getName).toList());
        }
    }

    public void setPriorities(List<PriorityLevel> priorities) {
        this.priorities = priorities;
        if (priorities != null) {
            priorityCombo.getItems().setAll(priorities.stream().map(PriorityLevel::getLevel).toList());
        }
    }


    @FXML
    private void onSaveTask() {
        if (currentTask == null) { // New Task
            currentTask = new Task(
                    titleField.getText(),
                    descriptionField.getText(),
                    categoryCombo.getValue(),
                    priorityCombo.getValue(),
                    dueDatePicker.getValue(),
                    statusCombo.getValue()
                    );
        }
        else {
            // Update the current task
            currentTask.setTitle(titleField.getText());
            currentTask.setDescription(descriptionField.getText());
            currentTask.setCategory(categoryCombo.getValue());
            currentTask.setPriority(priorityCombo.getValue());
            currentTask.setStatus(statusCombo.getValue());
            currentTask.setDueDate(dueDatePicker.getValue());
        }

        // Close the window
        closeWindow();
    }

    @FXML
    private void onDeleteTask() {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this task?",
                ButtonType.YES, ButtonType.NO);
        confirmation.setTitle("Delete Task");
        confirmation.setHeaderText(null);

        confirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                taskDeleted = true;
                closeWindow();
            }
        });
    }

    public Task getTask() {
        return taskDeleted ? null : currentTask;
    }

    private void closeWindow() {
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }
}
