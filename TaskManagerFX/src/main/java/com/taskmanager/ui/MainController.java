package com.taskmanager.ui;

import com.taskmanager.model.Category;
import com.taskmanager.model.PriorityLevel;
import com.taskmanager.model.Reminder;
import com.taskmanager.model.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;


/**
 * Controller class for the main interface of the Medialab Assistant.
 * This is the most important class in the application!
 *
 * This controller manages tasks, categories, priorities, and reminders.
 * It handles user interactions with the Main UI page and updates it accordingly.
 * Provides functionality for adding, editing, searching, and calculating statistics.
 */
public class MainController {
    @FXML private ListView<Task> taskListView;
    @FXML private ListView<Category> categoryListView;
    @FXML private ListView<PriorityLevel> priorityListView;
    @FXML private ListView<Reminder> reminderListView;
    private List<Task> tasks;
    private List<Category> categories;
    private List<PriorityLevel> priorities;
    private List<Reminder> reminders;

    // For Statistics
    @FXML private Label totalTasksLabel;
    @FXML private Label completedTasksLabel;
    @FXML private Label delayedTasksLabel;
    @FXML private Label dueSoonTasksLabel;

    // For Search
    @FXML private TextField searchTitleField;
    @FXML private ComboBox<Category> searchCategoryCombo;
    @FXML private ComboBox<PriorityLevel> searchPriorityCombo;
    @FXML private ListView<Task> searchResultsListView;



    /**
     * Initializes the controller by setting up event handlers and preparing the UI.
     * Configures double-click actions for editing tasks, categories, priorities, and reminders.
     */
    @FXML
    public void initialize() {
        // Allow the user to interact with the list views
        // -- Tasks --
        taskListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double-click to edit
                Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
                if (selectedTask != null) {
                    editTask(selectedTask);
                }
            }
        });

        // -- Categories --
        categoryListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Category selectedCategory = categoryListView.getSelectionModel().getSelectedItem();
                if (selectedCategory != null) {
                    System.out.println("Selected Category: " + selectedCategory.getName());
                    editCategory(selectedCategory);
                }
            }
        });

        // -- Priorities --
        priorityListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                PriorityLevel selectedPriority = priorityListView.getSelectionModel().getSelectedItem();
                if (selectedPriority != null) {
                    System.out.println("Selected Priority: " + selectedPriority.getLevel());
                    editPriority(selectedPriority);
                }
            }
        });

        // -- Reminders --
        reminderListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Reminder selectedReminder = reminderListView.getSelectionModel().getSelectedItem();
                if (selectedReminder != null) {
                    System.out.println("Selected Reminder: " + selectedReminder);
                    EditReminder();
                }
            }
        });

        // -- Search --
        searchResultsListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Task selectedTask = searchResultsListView.getSelectionModel().getSelectedItem();
                if (selectedTask != null) {
                    editTask(selectedTask);
                }
            }
        });

    }

    // Setters for the data. Used by MainApp to pass the data from JSON files to the MainController.

    /**
     * Sets the list of categories available in the system and updates the UI.
     * @param categories the list of Category objects to be displayed and used in the system.
     */
    public void setCategories(List<Category> categories) {
        this.categories = categories;
        categoryListView.getItems().addAll(categories);
        searchCategoryCombo.getItems().addAll(categories);
    }

    /**
     * Sets the list of priority levels available in the system and updates the UI.
     * @param priorities the list of PriorityLevel objects to be displayed and used in the system.
     */
    public void setPriorities(List<PriorityLevel> priorities) {
        this.priorities = priorities;
        priorityListView.getItems().addAll(priorities);
        searchPriorityCombo.getItems().addAll(priorities);
    }

    /**
     * Sets the list of tasks available in the system and updates the UI.
     *
     * @param tasks the list of Task objects to be displayed and managed.
     */
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        taskListView.getItems().addAll(tasks);
        // sort the tasks by category
        taskListView.getItems().sort((task1, task2) -> task1.getCategory().compareTo(task2.getCategory()));
    }

    /**
     * Sets the list of reminders available in the system and updates the UI.
     *
     * @param reminders the list of Reminder objects to be displayed and managed.
     */
    public void setReminders(List<Reminder> reminders) {
        this.reminders = reminders;
        reminderListView.getItems().addAll(reminders);
    }


    // Event Handlers. Adding, Editing, and Deleting tasks, categories, priorities, and reminders.

    // NOTE:
    // These methods don't create new objects but open dialogs for the user to input data.
    // After the creation or the editing of the object from the corresponding controller
    // these methods update the data structures shown in the UI

    // -- Tasks --
    @FXML
    private void onAddTask() {
        // New Dialog to create a task
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/task_management.fxml"));
            Parent root = loader.load();

            TaskController controller = loader.getController();
            // Here I don't pass any task because I want to create a new one

            // Pass the categories and priorities to the controller
            controller.setCategories(categories);
            controller.setPriorities(priorities);

            // Show the dialog and wait until the user closes it
            Stage stage = new Stage();
            stage.setTitle("Add New Task");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            Task newTask = controller.getTask();
            if (newTask != null) {
                tasks.add(newTask);
                taskListView.getItems().add(newTask);
                // sort
                taskListView.getItems().sort((task1, task2) -> task1.getCategory().compareTo(task2.getCategory()));
                updateStatistics(); // Update statistics
                //JSONHandler.saveTasks(tasks); // Save task only at the end of the application!
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void editTask(Task task) {
        // New Dialog to Edit a Task!
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/task_management.fxml"));
            Parent root = loader.load();

            TaskController controller = loader.getController();
            controller.setTask(task); // Pass the selected task to the controllerα

            // Pass the categories and priorities to the controller
            controller.setCategories(categories);
            controller.setPriorities(priorities);


            // Show the dialog and wait until the user closes it
            Stage stage = new Stage();
            stage.setTitle("Edit Task");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Update task details
            // Get the updated task (null if deleted)
            Task updatedTask = controller.getTask();

            if (updatedTask == null) {
                // Task was deleted
                tasks.remove(task);
                taskListView.getItems().remove(task);
                // Remove all reminders for this task
                reminders.removeIf(reminder -> reminder.getTaskId().equals(task.getId()));
                reminderListView.getItems().removeIf(reminder -> reminder.getTaskId().equals(task.getId()));
            } else {
                // Refresh UI for modified task
                taskListView.refresh();
                //sort
                taskListView.getItems().sort((task1, task2) -> task1.getCategory().compareTo(task2.getCategory()));
                // Manage the reminders
                // We will use Iterator to remove the completed reminders
                Iterator<Reminder> iterator = reminders.iterator();
                while (iterator.hasNext()) {
                    Reminder reminder = iterator.next();
                    if (reminder.getTaskId().equals(updatedTask.getId())) {
                        if ("Completed".equals(updatedTask.getStatus())) {
                            iterator.remove();
                            reminderListView.getItems().remove(reminder);
                            System.out.println("Reminder removed for completed task");
                        } else {
                            reminder.setTaskTitle(updatedTask.getTitle());
                        }
                    }
                }
                reminderListView.refresh();
            }
            updateStatistics(); // Update statistics
            //JSONHandler.saveTasks(tasks); // Save updated task list to JSON
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // -- Categories --
    @FXML
    private void onAddCategory() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/category_management.fxml"));
        try {
            Parent root = loader.load();
            CategoryController controller = loader.getController();
            Stage stage = new Stage();
            stage.setTitle("Add New Category");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            Category newCategory = controller.getCategory();
            if (newCategory != null) {
                categories.add(newCategory);
                categoryListView.getItems().add(newCategory);
                //JSONHandler.saveCategories(categories);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void editCategory(Category category) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/category_management.fxml"));
            Parent root = loader.load();

            CategoryController controller = loader.getController();
            controller.setCategory(category);

            Stage stage = new Stage();
            stage.setTitle("Edit Category");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            Category updatedCategory = controller.getCategory();

            if (updatedCategory == null) {
                categories.remove(category);
                categoryListView.getItems().remove(category);
                // Delete all tasks with this category
                tasks.removeIf(task -> task.getCategory().equals(category));
                taskListView.getItems().removeIf(task -> task.getCategory().equals(category));
                taskListView.refresh();
                updateStatistics(); // Update statistics

            } else {
                categoryListView.refresh();
                taskListView.refresh(); // Tasks with this category need to be updated
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // -- Priorities --
    @FXML
    private void onAddPriority() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/priority_management.fxml"));
        try {
            Parent root = loader.load();
            PriorityController controller = loader.getController();
            Stage stage = new Stage();
            stage.setTitle("Add New Priority");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            PriorityLevel newPriority = controller.getPriority();
            if (newPriority != null) {
                priorities.add(newPriority);
                priorityListView.getItems().add(newPriority);
                //JSONHandler.savePriorities(priorities);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void editPriority(PriorityLevel priority) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/priority_management.fxml"));
            Parent root = loader.load();

            PriorityController controller = loader.getController();
            controller.setPriority(priority);

            Stage stage = new Stage();
            stage.setTitle("Edit Priority");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            PriorityLevel updatedPriority = controller.getPriority();

            // If the priority is deleted, remove it from the list and update the tasks
            if (updatedPriority == null) {
                priorities.remove(priority);
                priorityListView.getItems().remove(priority);
                // Set all tasks with this priority to "Default"
                tasks.forEach(task -> {
                    if (task.getPriority().equals(priority)) {
                        task.setPriority(priorities.get(0));
                    }
                });
                updateStatistics(); // Update statistics
            } else {
                priorityListView.refresh();
                taskListView.refresh(); // Tasks with this priority need to be updated
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // -- Reminders --
    @FXML
    private void onAddReminder() {
        Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
        if (selectedTask == null) {
            showAlert("Error", "Please select a task first.");
            return;
        }

        if ("Completed".equals(selectedTask.getStatus())) {
            showAlert("Error", "Cannot add reminders to a completed task.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/reminder_management.fxml"));
            Parent root = loader.load();

            ReminderController controller = loader.getController();
            controller.setTask(selectedTask);

            Stage stage = new Stage();
            stage.setTitle("Add Reminder");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            Reminder newReminder = controller.getReminder();
            if (newReminder != null) {
                reminders.add(newReminder);
                reminderListView.getItems().add(newReminder);
                //JSONHandler.saveReminders(reminders);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void EditReminder() {
        Reminder selectedReminder = reminderListView.getSelectionModel().getSelectedItem();
        if (selectedReminder == null) {
            showAlert("Error", "Please select a reminder first.");
            return;
        }

        Task task = tasks.stream()
                .filter(t -> t.getId().equals(selectedReminder.getTaskId()))
                .findFirst()
                .orElse(null);

        // print the task
        System.out.println(task);

        if (task == null) {
            showAlert("Error", "Task not found for this reminder.");
            return;
        }

        if ("Completed".equals(task.getStatus())) {
            showAlert("Error", "Cannot edit reminders for a completed task.");
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/reminder_management.fxml"));
            Parent root = loader.load();

            ReminderController controller = loader.getController();
            controller.setReminder(selectedReminder);
            controller.setTask(task);

            Stage stage = new Stage();
            stage.setTitle("Edit Reminder");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Get the updated reminder (null if deleted)
            Reminder updatedReminder = controller.getReminder();
            // print
            System.out.println(updatedReminder);
            if (updatedReminder == null) {
                reminders.remove(selectedReminder);
                reminderListView.getItems().remove(selectedReminder);
            } else {
                reminderListView.refresh();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Updates the task statistics displayed in the UI, including total, completed, delayed, and due soon tasks.
     */
    public void updateStatistics() {
        // Υπολογισμός των στατιστικών
        int totalTasks = tasks.size();
        int completedTasks = (int) tasks.stream().filter(task -> "Completed".equals(task.getStatus())).count();
        int delayedTasks = (int) tasks.stream().filter(task -> "Delayed".equals(task.getStatus())).count();
        int dueSoonTasks = (int) tasks.stream()
                .filter(task -> task.getDueDate() != null && !task.getDueDate().isBefore(LocalDate.now())
                        && task.getDueDate().isBefore(LocalDate.now().plusDays(7)))
                .count();
        // Ενημέρωση των Labels
        totalTasksLabel.setText("Total Tasks: " + totalTasks);
        completedTasksLabel.setText("Completed Tasks: " + completedTasks);
        delayedTasksLabel.setText("Delayed Tasks: " + delayedTasks);
        dueSoonTasksLabel.setText("Due in 7 Days: " + dueSoonTasks);
    }

    // -- Search --
    @FXML
    private void onSearchTasks() {
        // Λήψη τιμών από τα πεδία αναζήτησης
        String titleQuery = searchTitleField.getText().trim().toLowerCase();
        Category selectedCategory = searchCategoryCombo.getValue();
        PriorityLevel selectedPriority = searchPriorityCombo.getValue();

        // Φιλτράρισμα των εργασιών
        List<Task> filteredTasks = tasks.stream()
                .filter(task -> (titleQuery.isEmpty() || task.getTitle().toLowerCase().contains(titleQuery)))
                .filter(task -> (selectedCategory == null || task.getCategory().equals(selectedCategory)))
                .filter(task -> (selectedPriority == null || task.getPriority().equals(selectedPriority)))
                .toList();

        // Ενημέρωση της λίστας αποτελεσμάτων
        searchResultsListView.getItems().setAll(filteredTasks);
    }
    @FXML
    private void onClearSearchFields() {
        // Καθαρισμός πεδίων
        searchTitleField.clear();
        searchCategoryCombo.getSelectionModel().clearSelection();
        searchPriorityCombo.getSelectionModel().clearSelection();
        searchResultsListView.getItems().clear(); // Καθαρισμός αποτελεσμάτων
    }

    /**
     * Checks for delayed tasks in the system and displays an alert if any are found.
     * Used by MainApp to check for delayed tasks when the application starts and notifies the user.
     * @param tasks the list of tasks to check for delays.
     */
    public void checkForDelayedTasks(List<Task> tasks) {
        long delayedTasksCount = tasks.stream()
                .filter(task -> "Delayed".equals(task.getStatus()))
                .count();

        if (delayedTasksCount > 0) {
            showDelayedTasksAlert(delayedTasksCount, tasks);
        }
    }

    private void showDelayedTasksAlert(long delayedTasksCount, List<Task> tasks) {
        String delayedTasksDetails = tasks.stream()
                .filter(task -> "Delayed".equals(task.getStatus()))
                .map(task -> task.getTitle() + " (Προθεσμία: " + task.getDueDate() + ")")
                .reduce("", (acc, taskDetails) -> acc + taskDetails + "\n");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ειδοποίηση Εκπρόθεσμων Εργασιών");
        alert.setHeaderText("Υπάρχουν Εκπρόθεσμες Εργασίες");
        alert.setContentText("Πλήθος Εκπρόθεσμων Εργασιών: " + delayedTasksCount + "\n\n" + delayedTasksDetails);

        alert.showAndWait();
    }

    // Help method to show alerts
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
