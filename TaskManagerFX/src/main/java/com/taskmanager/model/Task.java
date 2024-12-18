package com.taskmanager.model;

import java.time.LocalDate;
import java.util.UUID;

public class Task {
    private String title;
    private String description;
    private String category;
    private String priority;
    private LocalDate dueDate;
    private String status;
    private UUID id;
    // Το id ίσως και να μην χρειάζεται τελικά!

    // Status can take values:  "Open", "In Progress", "Postponed", "Completed", "Delayed"

    public Task(String title, String description, String category, String priority, LocalDate dueDate, String status) {
        this.title = title;
        this.description = description;
        this.category = category;
        this.priority = priority;
        this.dueDate = dueDate;
        this.status = status;
        this.id = UUID.randomUUID();
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
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
    }

    @Override
    public String toString() {
        return title + " (" + status + ")";
    }

    public UUID getId() {
        return id;
    }

    // Creating New Task

    // Modifying a Task

    // Deleting a Task

}
