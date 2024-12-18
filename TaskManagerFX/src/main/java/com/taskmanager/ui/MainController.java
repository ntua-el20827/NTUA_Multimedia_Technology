package com.taskmanager.ui;

import com.taskmanager.json.JSONHandler;
import com.taskmanager.model.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MainController {

    @FXML
    private ListView<Task> taskListView;
    private List<Task> tasks;
    // Static γιατι θελω η getTitles() να είναι static, να αφορά δηλαδή την κλάση και οχι το αντικείμενο!
    public static List<UUID> ids;

    @FXML
    public void initialize() {
        // Load Tasks from Json files
        tasks = JSONHandler.loadTasks();

        // Αν υπάρχουν tasks, προστίθενται στο ListView
        if (tasks != null) {
            taskListView.getItems().addAll(tasks);
        }

        // Get all titles
        ids = tasks.stream().map(Task::getId).collect(Collectors.toList());

        // Allow the user to select a task from the ListView. Make them Clickable!
        taskListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double-click to edit
                Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
                if (selectedTask != null) {
                    editTask(selectedTask);
                }
            }
        });
    }

    @FXML
    public List<Task> getTasks() {
        return tasks;
    }

    @FXML
    public static List<UUID> getIds() {
        return ids;
    }


    @FXML
    private void onAddTask() {
        // New Dialog to create a task
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/task_management.fxml"));
            Parent root = loader.load();

            TaskController controller = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Add New Task");
            stage.setScene(new Scene(root));
            stage.showAndWait();
            Task newTask = controller.getCreatedTask();
            if (newTask != null) {
                tasks.add(newTask);
                taskListView.getItems().add(newTask);
                JSONHandler.saveTasks(tasks); // Save updated task list to JSON
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

            // Show the dialog and wait until the user closes it
            Stage stage = new Stage();
            stage.setTitle("Edit Task");
            stage.setScene(new Scene(root));
            stage.showAndWait();

            // Update task details
            taskListView.refresh();
            //JSONHandler.saveTasks(tasks); // Save updated task list to JSON
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
