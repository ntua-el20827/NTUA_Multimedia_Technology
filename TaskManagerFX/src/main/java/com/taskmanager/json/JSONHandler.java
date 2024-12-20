package com.taskmanager.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder; // Create Gson object in a different way!
import com.google.gson.reflect.TypeToken;
import com.taskmanager.model.Category;
import com.taskmanager.model.PriorityLevel;
import com.taskmanager.model.Task;


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JSONHandler {
    private static final String TASKS_FILE = "src/main/resources/medialab/tasks.json";
    private static final String CATEGORIES_FILE = "src/main/resources/medialab/categories.json";
    private static final String PRIORITIES_FILE = "src/main/resources/medialab/priorities.json";

    // Φόρτωση εργασιών από το JSON αρχείο
    public static List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();
        //System.out.println(new File("src/main/resources/medialab/tasks.json").getAbsolutePath());
        //System.out.println(System.getProperty("java.class.path"));
        try (FileReader reader = new FileReader(TASKS_FILE)) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();
            Type taskListType = new TypeToken<List<Task>>() {}.getType();

            System.out.println("Okey thus far");
            // Working!
            // Problem with the Date! (dueDate is LocalDate)
            // (1) Fix the problem
            // (2) Make dueDate String
            // Chose solution (1). Πρεπει να είναι σε αυτό το format αναγκαστικα!
            // Αρα θα φτιάξω εναν custom Type Adapter για να μπορεί να χρησιμοποιήσει το Gson.
            // Αναγκαστικά χρησιμοποιώ και το GsonBuilder και οχι τοα default!
            // Τωρα δουλεύει!
            tasks = gson.fromJson(reader, taskListType);
        } catch (IOException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
        }
        return tasks;
    }

    public static List<Category> loadCategories() {
        List<Category> categories = new ArrayList<>();
        try (FileReader reader = new FileReader(CATEGORIES_FILE)) {
            Gson gson = new Gson();
            Type categoryListType = new TypeToken<List<Category>>() {}.getType();
            categories = gson.fromJson(reader, categoryListType);
        } catch (IOException e) {
            System.out.println("Error loading categories: " + e.getMessage());
        }
        return categories;
    }

    public static List<PriorityLevel> loadPriorities() {
        List<PriorityLevel> priorities = new ArrayList<>();
        try (FileReader reader = new FileReader(PRIORITIES_FILE)) {
            Gson gson = new Gson();
            Type priorityListType = new TypeToken<List<PriorityLevel>>() {}.getType();
            priorities = gson.fromJson(reader, priorityListType);
        } catch (IOException e) {
            System.out.println("Error loading priorities: " + e.getMessage());
        }
        return priorities;
    }

    // Αποθήκευση εργασιών στο JSON αρχείο
    public static void saveTasks(List<Task> tasks) {
        try (FileWriter writer = new FileWriter(TASKS_FILE)) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .create();
            // Ομοίως και εδω χρησιμοποιώ τον GsonBuilder!
            gson.toJson(tasks, writer);
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    public static void saveCategories(List<Category> categories) {
        try (FileWriter writer = new FileWriter(CATEGORIES_FILE)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(categories, writer);
        } catch (IOException e) {
            System.out.println("Error saving categories: " + e.getMessage());
        }
    }

    public static <Priority> void savePriorities(List<Priority> priorities) {
        try (FileWriter writer = new FileWriter(PRIORITIES_FILE)) {
            Gson gson = new Gson();
            gson.toJson(priorities, writer);
        } catch (IOException e) {
            System.out.println("Error saving priorities: " + e.getMessage());
        }
    }
}
