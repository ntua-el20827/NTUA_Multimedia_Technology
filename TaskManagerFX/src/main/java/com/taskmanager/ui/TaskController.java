package com.taskmanager.ui;

import com.taskmanager.model.Category;
import com.taskmanager.model.PriorityLevel;
import com.taskmanager.model.StatusType;
import com.taskmanager.model.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Controller class for managing tasks in the Task Management Interface.
 * Provides methods to handle user interactions for creating, editing and deleting tasks.
 * Also, handles priority levels, categories, and
 */
public class TaskController {
    @FXML private TextField titleField;
    @FXML private TextArea descriptionField;
    @FXML private ComboBox<Category> categoryCombo;
    @FXML private ComboBox<PriorityLevel> priorityCombo;
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
        // Add values to the status combo box
        //statusCombo.getItems().addAll("Open", "In Progress", "Postponed", "Completed", "Delayed");

        // Use StatusType enum values to populate the status combo box
        for (StatusType status : StatusType.values()) {
            String displayName = status.name().charAt(0) + status.name().substring(1).toLowerCase().replace("_", " ");
            statusCombo.getItems().add(displayName);
        }
        // Check
        System.out.println(statusCombo.getItems());

        // Add dummy values to the category and priority combo boxes for testing purposes
        //categoryCombo.getItems().addAll("Work", "Personal", "Hobby");
        //priorityCombo.getItems().addAll("Low", "Medium", "High");
        deleteButton.setVisible(false);
    }

     /**
     * Sets the task to be edited or displayed in the controller.
     * Populates the fields in the UI with the task's data.
     *
     * @param task the Task object to be edited. If null, no task is preloaded.
     */
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

    /**
     * Sets the list of categories available for selection in the UI.
     * Updates the category combo box with the provided categories.
     *
     * @param categories a list of Category objects to populate the combo box.
     */
    public void setCategories(List<Category> categories) {
        this.categories = categories;
        if (categories != null) {
            categoryCombo.getItems().setAll(categories);
        }
    }


    /**
     * Sets the list of priority levels available for selection in the UI.
     * Updates the priority combo box with the provided priority levels.
     *
     * @param priorities a list of PriorityLevel objects to populate the combo box.
     */
    public void setPriorities(List<PriorityLevel> priorities) {
        this.priorities = priorities;
        if (priorities != null) {
            priorityCombo.getItems().setAll(priorities);
        }
    }


    // Handles "Save" button of the Task Management Interface.
    // Actually creating a new task (object) or updating an existing one.
    // Then the MainController will add it to the list shown in the UI.
    @FXML
    private void onSaveTask() {
        if (currentTask == null) { // New Task
            if (titleField.getText().isEmpty()) {
                showAlert("Validation Error", "Task title cannot be empty.");
                return;
            }
            if (dueDatePicker.getValue() == null) {
                showAlert("Validation Error", "Due date cannot be empty.");
                return;
            }
            if (categoryCombo.getValue() == null) {
                showAlert("Validation Error", "Category cannot be empty.");
                return;
            }
            if (priorityCombo.getValue() == null) {
                // priority = default
                priorityCombo.setValue(new PriorityLevel("Default"));
                //PriorityLevel priorityLevel_dummy = new PriorityLevel("Default");
            }
            if (statusCombo.getValue() == null) {
                // status = open
                statusCombo.setValue("Open");
            }
            currentTask = new Task(
                    titleField.getText(),
                    descriptionField.getText(),
                    categoryCombo.getValue(),
                    priorityCombo.getValue(),
                    dueDatePicker.getValue(),
                    statusCombo.getValue(),
                    UUID.randomUUID().toString()
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

    // Help method to show alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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

    /**
     * Retrieves the task that has been edited or created in the controller.
     * If the task was deleted, this method returns null.
     *
     * @return the Task object representing the edited or created task, or null if deleted.
     */
    public Task getTask() {
        return taskDeleted ? null : currentTask;
    }

    private void closeWindow() {
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }
}
