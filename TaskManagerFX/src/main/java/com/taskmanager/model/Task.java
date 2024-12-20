package com.taskmanager.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Task {
    private String title;
    private String description;
    private Category category;
    private PriorityLevel priority;
    private LocalDate dueDate;
    private String status;
    private String id;

    // Reminders
    private List<Reminder> reminders = new ArrayList<>();

    // Status can take values:  "Open", "In Progress", "Postponed", "Completed", "Delayed"

    public Task(String title, String description, Category category, PriorityLevel priority, LocalDate dueDate, String status, String id) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.priority = priority;
        this.dueDate = dueDate;
        this.status = status;
        this.id = id;
    }
    // Getters and Setters
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }
    public void setCategory(Category category) {
        this.category = category;
    }

    public PriorityLevel getPriority() {
        return priority;
    }
    public void setPriority(PriorityLevel priority) {
        this.priority = priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
        // If the task is completed, clear all reminders
        if (status.equals("Completed")) {
            clearReminders();
        }
    }

    public String getId() {return id;}
    public void setId(String id) {this.id = id;}

    @Override
    public String toString() {
        return title + " ( Status = " + status + "/ Category = " + category + "/ Priority = " + priority + "/ Due Date = " + dueDate + " )";
        //return title;
    }

    // Reminder methods
    public List<Reminder> getReminders() {
        return reminders;
    }

    public void addReminder(Reminder reminder) {
        this.reminders.add(reminder);
    }

    public void setReminders(List<Reminder> reminders) {
        this.reminders = reminders;
    }

    public void removeReminder(Reminder reminder) {
        this.reminders.remove(reminder);
    }

    public void clearReminders() {
        this.reminders.clear();
    }

}
