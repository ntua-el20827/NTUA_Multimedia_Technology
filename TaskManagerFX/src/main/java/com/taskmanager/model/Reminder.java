package com.taskmanager.model;

import java.time.LocalDate;

public class Reminder {
    private String taskId;
    private String taskTitle;
    private ReminderType type;
    private LocalDate reminderDate;

    public Reminder(String taskId, ReminderType type, LocalDate reminderDate) {
        this.taskId = taskId;
        this.type = type;
        this.reminderDate = reminderDate;
    }

    public String getTaskId() {return taskId;}
    public void setTaskId(String taskId) {this.taskId = taskId;}

    public String getTaskTitle() {return taskTitle;}
    public void setTaskTitle(String taskTitle) {this.taskTitle = taskTitle;}

    public ReminderType getType() {return type;}
    public void setType(ReminderType type) {this.type = type;}

    public LocalDate getReminderDate() {return reminderDate;}
    public void setReminderDate(LocalDate reminderDate) {this.reminderDate = reminderDate;}

    @Override
    public String toString() {
        return "Reminder for " + taskTitle + " (type=" + type + ", date=" + reminderDate + ")";
    }


}
