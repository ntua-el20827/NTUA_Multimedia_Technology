package com.taskmanager.ui;

import com.taskmanager.json.JSONHandler;
import com.taskmanager.model.Category;
import com.taskmanager.model.PriorityLevel;
import com.taskmanager.model.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class MainController {
    @FXML private ListView<Task> taskListView;
    @FXML private ListView<Category> categoryListView;
    @FXML private ListView<PriorityLevel> priorityListView;
    private List<Task> tasks;
    private List<Category> categories;
    private List<PriorityLevel> priorities;

    // For Statistics
    @FXML private Label totalTasksLabel;
    @FXML private Label completedTasksLabel;
    @FXML private Label delayedTasksLabel;
    @FXML private Label dueSoonTasksLabel;


    // Initialize. Load tasks from json files.
    @FXML
    public void initialize() {
        // -- Tasks --
        tasks = JSONHandler.loadTasks();
        updateStatistics(); // Update statistics
        if (totalTasksLabel == null || completedTasksLabel == null || delayedTasksLabel == null || dueSoonTasksLabel == null) {
            System.out.println("Error: Labels not initialized properly!");
        } else {
            System.out.println("Labels initialized correctly.");
        }
        // Fill ListView
        if (tasks != null) {
            taskListView.getItems().addAll(tasks);
        }

        // Allow the user to select a task from the ListView. Make them Clickable!
        taskListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double-click to edit
                Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
                if (selectedTask != null) {
                    editTask(selectedTask);
                }
            }
        });

        // -- Categories --
        categories = JSONHandler.loadCategories();
        if (categories != null) {
            // Fill ListView with names of categories
            categoryListView.getItems().addAll(categories);
        }
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
        priorities = JSONHandler.loadPriorities();
        if (priorities != null) {
            // Fill ListView with names of priorities
            priorityListView.getItems().addAll(priorities);
        }
        priorityListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                PriorityLevel selectedPriority = priorityListView.getSelectionModel().getSelectedItem();
                if (selectedPriority != null) {
                    System.out.println("Selected Priority: " + selectedPriority.getLevel());
                    editPriority(selectedPriority);
                }
            }
        });

    }


    // Tasks
    @FXML
    public List<Task> getTasks() {
        return tasks;
    }

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
            controller.setTask(task); // Pass the selected task to the controller

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
            } else {
                // Refresh UI for modified task
                taskListView.refresh();
            }
            updateStatistics(); // Update statistics
            //JSONHandler.saveTasks(tasks); // Save updated task list to JSON
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Categories
    @FXML
    public List<Category> getCategories() {
        return categories;
    }
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
                tasks.removeIf(task -> task.getCategory().equals(category.getName()));
                //taskListView.refresh();
                taskListView.getItems().removeIf(task -> task.getCategory().equals(category.getName()));
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
    public List<PriorityLevel> getPriorities() {
        return priorities;
    }

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
                    if (task.getPriority().equals(priority.getLevel())) {
                        task.setPriority("Default");
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



    //Statistics
    private void updateStatistics() {
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
