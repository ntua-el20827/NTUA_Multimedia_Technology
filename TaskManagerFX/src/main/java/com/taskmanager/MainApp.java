package com.taskmanager;

import com.taskmanager.json.JSONHandler;
import com.taskmanager.model.Category;
import com.taskmanager.model.PriorityLevel;
import com.taskmanager.model.Task;
import com.taskmanager.model.Reminder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

import com.taskmanager.ui.MainController;

/**
 * Main application class for the Task Management System.
 * Loads data, initializes the user interface, and manages application lifecycle.
 */
public class MainApp extends Application {
    /**
     * Starts the JavaFX application by loading data, setting up the primary stage, and initializing the UI.
     * This method is called by the JavaFX runtime when the application is launched.
     * @param primaryStage the primary stage for this application.
     * @throws Exception if there is an error during application startup.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Data is loaded from JSON files using the JSONHandler class (check com.taskmanager.json.JSONHandler)

            // Load Categories and Priorities first
            List<Category> categories = JSONHandler.loadCategories();
            List<PriorityLevel> priorities = JSONHandler.loadPriorities();

            // Load Tasks and connect them with Categories and Priorities
            List<Task> tasks = JSONHandler.loadTasks(categories, priorities);

            // Load Reminders and connect them with Tasks
            List<Reminder> reminders = JSONHandler.loadReminders();
            for (Reminder reminder : reminders) {
                Task task = tasks.stream()
                        .filter(t -> t.getId().equals(reminder.getTaskId()))
                        .findFirst()
                        .orElse(null);
                if (task != null) {
                    task.getReminders().add(reminder);
                    reminder.setTaskTitle(task.getTitle());
                }
            }

            // Check the objects that have been created
            categories.forEach(category -> System.out.println(category));
            priorities.forEach(priority -> System.out.println(priority));
            tasks.forEach(task -> System.out.println(task));

            // Load the main FXML layout
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/main.fxml"));
            Parent root = loader.load();

            // Get the Main Controller of the App and pass the data to it.
            MainController controller = loader.getController();
            controller.setCategories(categories);
            controller.setPriorities(priorities);
            controller.setTasks(tasks);
            controller.setReminders(reminders);
            controller.updateStatistics();


            // Create the main scene and set it on the primary stage
            Scene scene = new Scene(root, 800, 700);
            primaryStage.setTitle("Media Lab Assistant");
            primaryStage.setScene(scene);

            // A handler for the close event of the primary stage. When the user exits the application the data is saved to JSON files.
            primaryStage.setOnCloseRequest(event -> {
                JSONHandler.saveTasks(tasks);
                JSONHandler.saveCategories(categories);
                JSONHandler.savePriorities(priorities);
                JSONHandler.saveReminders(reminders);
                System.out.println("All saved to JSON");
            });

            primaryStage.show();

            // Check for delayed tasks
            controller.checkForDelayedTasks(tasks);
            // Here is the right place for this check as I want the application to be opened first!
        } catch (IOException e) {
            System.out.println("Something went wrong! (again...)");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
