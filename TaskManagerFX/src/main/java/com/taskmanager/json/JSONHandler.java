package com.taskmanager.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder; // Create Gson object in a different way!
import com.google.gson.reflect.TypeToken;
import com.taskmanager.model.Category;
import com.taskmanager.model.PriorityLevel;
import com.taskmanager.model.Reminder;
import com.taskmanager.model.Task;
import javafx.scene.layout.Priority;


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
    private static final String REMINDERS_FILE = "src/main/resources/medialab/reminders.json";

    // Φόρτωση εργασιών από το JSON αρχείο
    public static List<Task> loadTasks(List<Category> categories, List<PriorityLevel> priorities) {
        List<Task> tasks = new ArrayList<>();
        try (FileReader reader = new FileReader(TASKS_FILE)) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()) // Για LocalDate
                    .create();
            Type taskListType = new TypeToken<List<Task>>() {}.getType();
            List<Task> loadedTasks = gson.fromJson(reader, taskListType);

            for (Task task : loadedTasks) {
                // Συνδέουμε την κατηγορία
                Category category = categories.stream()
                        .filter(cat -> cat.getName().equals(task.getCategory().getName()))
                        .findFirst()
                        .orElse(null);
                task.setCategory(category);

                // Συνδέουμε την προτεραιότητα
                PriorityLevel priority = priorities.stream()
                        .filter(pri -> pri.getLevel().equals(task.getPriority().getLevel()))
                        .findFirst()
                        .orElse(null);
                task.setPriority(priority);

                tasks.add(task);
            }
            // Διασφαλίζουμε ότι η λίστα reminders δεν είναι null
            for (Task task : tasks) {
                if (task.getReminders() == null) {
                    task.setReminders(new ArrayList<>());
                }
            }

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

    public static List<Reminder> loadReminders() {
        List<Reminder> reminders = new ArrayList<>();
        try (FileReader reader = new FileReader(REMINDERS_FILE)) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()) // Για το LocalDate
                    .create();
            Type reminderListType = new TypeToken<List<Reminder>>() {}.getType();
            reminders = gson.fromJson(reader, reminderListType);
        } catch (IOException e) {
            System.out.println("Error loading reminders: " + e.getMessage());
        }
        return reminders;
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

    public static void savePriorities(List<PriorityLevel> priorities) {
        try (FileWriter writer = new FileWriter(PRIORITIES_FILE)) {
            Gson gson = new Gson();
            gson.toJson(priorities, writer);
        } catch (IOException e) {
            System.out.println("Error saving priorities: " + e.getMessage());
        }
    }

    public static void saveReminders(List<Reminder> reminders) {
        try (FileWriter writer = new FileWriter(REMINDERS_FILE)) {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()) // Για το LocalDate
                    .create();
            gson.toJson(reminders, writer);
        } catch (IOException e) {
            System.out.println("Error saving reminders: " + e.getMessage());
        }
    }

}
