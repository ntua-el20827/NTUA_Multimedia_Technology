package com.taskmanager.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a task in the task management system.
 * Based on the assignment, a task has a title, description, category, priority, due date, status, and associated reminders.
 * I also gave each task a unique identifier (id) using the UUID class.
 */
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
    // Check StatusType enum

    /**
     * Constructs a new Task with the specified details.
     *
     * @param title the title of the task.
     * @param description a brief description of the task.
     * @param category the category to which the task belongs.
     * @param priority the priority level of the task.
     * @param dueDate the due date for the task.
     * @param status the initial status of the task.
     * @param id the unique identifier for the task.
     */
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
    /**
     * Returns the title of the task.
     *
     * @return the title of the task.
     */
    public String getTitle() {return title;}
    /**
     * Sets the title of the task.
     *
     * @param title the title of the task.
     */
    public void setTitle(String title) {this.title = title;}

    /**
     * Returns a brief description of the task.
     *
     * @return a brief description of the task.
     */
    public String getDescription() {return description;}
    /**
     * Sets a brief description of the task.
     *
     * @param description a brief description of the task.
     */
    public void setDescription(String description) {this.description = description;}

    /**
     * Returns the category to which the task belongs.
     *
     * @return the category to which the task belongs.
     */
    public Category getCategory() {return category;}
    /**
     * Sets the category to which the task belongs.
     *
     * @param category the category to which the task belongs.
     */
    public void setCategory(Category category) {this.category = category;}

    /**
     * Returns the priority level of the task.
     *
     * @return the priority level of the task.
     */
    public PriorityLevel getPriority() {return priority;}
    /**
     * Sets the priority level of the task.
     *
     * @param priority the priority level of the task.
     */
    public void setPriority(PriorityLevel priority) {this.priority = priority;}

    /**
     * Returns the due date for the task.
     *
     * @return the due date for the task.
     */
    public LocalDate getDueDate() {return dueDate;}
    /**
     * Sets the due date for the task.
     *
     * @param dueDate the due date for the task.
     */
    public void setDueDate(LocalDate dueDate) {this.dueDate = dueDate;}

    /**
     * Returns the status of the task.
     *
     * @return the status of the task.
     */
    public String getStatus() {return status;}
    /**
     * Sets the status of the task.
     *
     * @param status the status of the task.
     */
    public void setStatus(String status) {
        this.status = status;
        // If the task is completed, clear all reminders
        if (status.equals("Completed")) {
            clearReminders();
        }
    }

    /**
     * Returns the unique identifier for the task.
     *
     * @return the unique identifier for the task.
     */
    public String getId() {return id;}
    public void setId(String id) {this.id = id;}

    /**
     * Returns a string representation of the task.
     *
     * @return a string representation of the task.
     */
    @Override
    public String toString() {
        return title + " ( Category = " + category + " / Status = " + status + " / Priority = " + priority + " / Due Date = " + dueDate + " )";
        //return title;
    }

    // Reminder methods
    /**
     * Returns the list of reminders associated with the task.
     *
     * @return the list of reminders associated with the task.
     */
    public List<Reminder> getReminders() {return reminders;}

    /**
     * Adds a reminder to the task.
     *
     * @param reminder the reminder to add to the task.
     */
    public void addReminder(Reminder reminder) {this.reminders.add(reminder);}

    /**
     * Sets the list of reminders associated with the task.
     *
     * @param reminders the list of reminders associated with the task.
     */
    public void setReminders(List<Reminder> reminders) {this.reminders = reminders;}

    /**
     * Removes a reminder from the task.
     *
     * @param reminder the reminder to remove from the task.
     */
    public void removeReminder(Reminder reminder) {this.reminders.remove(reminder);}

    /**
     * Clears all reminders associated with the task.
     */
    public void clearReminders() {this.reminders.clear();}

}
