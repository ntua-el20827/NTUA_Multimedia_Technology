package com.taskmanager.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.taskmanager.model.Task;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JSONHandler {
    private static final String TASKS_FILE = "src/main/resources/medialab/tasks.json";

    // Φόρτωση εργασιών από το JSON αρχείο
    public static List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        //System.out.println(new File("src/main/resources/medialab/tasks.json").getAbsolutePath());
        //System.out.println(System.getProperty("java.class.path"));
        try (FileReader reader = new FileReader(TASKS_FILE)) {
            Gson gson = new Gson();
            Type taskListType = new TypeToken<List<Task>>() {}.getType();

            System.out.println("Okey thus far");
            // Working!
            // Problem with the Date! (dueDate is LocalDate)
            // (1) Fix the problem
            // (2) Make dueDate String
            // Chose solution (1). Πρεπει να είναι σε αυτό το format αναγκαστικα!




            tasks = gson.fromJson(reader, taskListType);
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return tasks;
    }

    // Αποθήκευση εργασιών στο JSON αρχείο
    public static void saveTasks(List<Task> tasks) {
        try (FileWriter writer = new FileWriter(TASKS_FILE)) {
            Gson gson = new Gson();
            gson.toJson(tasks, writer);
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }
}
