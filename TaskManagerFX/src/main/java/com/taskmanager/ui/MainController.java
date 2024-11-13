package com.taskmanager.ui;

import com.taskmanager.model.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import java.time.LocalDate;

public class MainController {

    @FXML
    private ListView<Task> taskListView;

    @FXML
    public void initialize() {
        // Create a dummy task and add it to the ListView
        Task dummyTask = new Task(
                "Study for Exam",
                "Prepare for the upcoming Java exam.",
                "School",
                "High",
                LocalDate.of(2024, 12, 1),
                "Open"
        );

        taskListView.getItems().add(dummyTask);
    }
}
