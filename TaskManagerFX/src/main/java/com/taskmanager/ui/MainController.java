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


    // Initialize. Load tasks from json files.
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
    }

    // On Initialization from MainApp, configure the tasks, categories, and priorities
    public void setCategories(List<Category> categories) {
        this.categories = categories;
        categoryListView.getItems().addAll(categories); // Ενημερώνει το GUI
    }

    public void setPriorities(List<PriorityLevel> priorities) {
        this.priorities = priorities;
        priorityListView.getItems().addAll(priorities); // Ενημερώνει το GUI
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        taskListView.getItems().addAll(tasks); // Ενημερώνει το GUI
    }

    public void setReminders(List<Reminder> reminders) {
        // Εδώ θα πρέπει να συνδέσουμε τις υπενθυμίσεις με τις εργασίες
        this.reminders = reminders;
        reminderListView.getItems().addAll(reminders); // Ενημερώνει το GUI
    }



    // Tasks

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

            Stage stage = new Stage();
            stage.setTitle("Add New Task");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            Task newTask = controller.getTask();
            if (newTask != null) {
                tasks.add(newTask);
                taskListView.getItems().add(newTask);
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

    // Categories
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Priorities
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // Reminder
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

            Stage stage = new Stage();
            stage.setTitle("Edit Reminder");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    //Statistics
    public void updateStatistics() {
        int totalTasks = tasks.size();
        int completedTasks = (int) tasks.stream().filter(task -> "Completed".equals(task.getStatus())).count();
        int delayedTasks = (int) tasks.stream().filter(task -> "Delayed".equals(task.getStatus())).count();
        int dueSoonTasks = (int) tasks.stream()
                .filter(task -> task.getDueDate() != null && !task.getDueDate().isBefore(LocalDate.now())
                        && task.getDueDate().isBefore(LocalDate.now().plusDays(7)))
                .count();
        // Ενημέρωση Labels
        totalTasksLabel.setText("Total Tasks: " + totalTasks);
        completedTasksLabel.setText("Completed: " + completedTasks);
        delayedTasksLabel.setText("Delayed: " + delayedTasks);
        dueSoonTasksLabel.setText("Due in 7 Days: " + dueSoonTasks);
    }

}
