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
import java.util.stream.Collectors;

import com.taskmanager.ui.MainController;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // Φόρτωση των Categories και Priorities πρώτα
            List<Category> categories = JSONHandler.loadCategories();
            List<PriorityLevel> priorities = JSONHandler.loadPriorities();

            // Φόρτωση των Tasks με σύνδεση στις κατηγορίες και προτεραιότητες
            List<Task> tasks = JSONHandler.loadTasks(categories, priorities);

            // Φορτωση των υπενθυμίσεων
            List<Reminder> reminders = JSONHandler.loadReminders();
            // Σύνδεση των Reminders με τα Tasks
            for (Reminder reminder : reminders) {
                Task task = tasks.stream()
                        .filter(t -> t.getId().equals(reminder.getTaskId()))
                        .findFirst()
                        .orElse(null);
                if (task != null) {
                    task.getReminders().add(reminder);
                }
            }

            // Check Objects
            categories.forEach(category -> System.out.println(category));
            priorities.forEach(priority -> System.out.println(priority));
            tasks.forEach(task -> System.out.println(task));

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/main.fxml"));
            Parent root = loader.load();

            // Get the controller to pass the loaded data
            MainController controller = loader.getController();

            // Set the categories, priorities, and tasks in the controller
            controller.setCategories(categories);
            controller.setPriorities(priorities);
            controller.setTasks(tasks);
            controller.setReminders(reminders);

            controller.updateStatistics();

            Scene scene = new Scene(root, 800, 700);

            primaryStage.setTitle("Media Lab Assistant");
            primaryStage.setScene(scene);

            // Add event handler for when the window is closed
            primaryStage.setOnCloseRequest(event -> {
                // Save tasks, categories, and priorities to JSON when the application is closed
                JSONHandler.saveTasks(tasks);
                JSONHandler.saveCategories(categories);
                JSONHandler.savePriorities(priorities);
                JSONHandler.saveReminders(reminders);
                System.out.println("All saved to JSON");
            });

            primaryStage.show();
        } catch (IOException e) {
            System.out.println("Don't Know WTF!");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
