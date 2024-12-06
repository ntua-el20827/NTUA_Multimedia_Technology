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

public class MainController {

    @FXML
    private ListView<Task> taskListView;
    private List<Task> tasks;

    @FXML
    public void initialize() {
        // Φόρτωση των tasks από το JSON
        tasks = JSONHandler.loadTasks();

        // Αν υπάρχουν tasks, προστίθενται στο ListView
        if (tasks != null) {
            taskListView.getItems().addAll(tasks);
        }
    }

    @FXML
    private void onAddTask() {
        // Anoigei neo parathiro gia na ftiakso to task
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/task_management.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Add New Task");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
