package com.taskmanager;

import com.taskmanager.json.JSONHandler;
import com.taskmanager.model.Task;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

import com.taskmanager.ui.MainController;

public class MainApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/main.fxml"));
            Parent root = loader.load();

            // Get the controller to access the tasks
            MainController controller = loader.getController();
            // Retrieve the tasks from the controller (that were loaded during initialization)
            List<Task> tasks = controller.getTasks();  // Assuming you have a getter for tasks

            Scene scene = new Scene(root, 400, 300);

            primaryStage.setTitle("Task Manager");
            primaryStage.setScene(scene);

            // Add event handler for when the window is closed
            primaryStage.setOnCloseRequest(event -> {
                // Save tasks to JSON when the application is closed
                JSONHandler.saveTasks(tasks);
                System.out.println("Tasks have been saved.");
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
