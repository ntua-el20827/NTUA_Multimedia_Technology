package com.taskmanager.model;

public class PriorityLevel {
    private String level;

    public PriorityLevel(String level) {
        if (level == null) {
            this.level = "Default";
        }
        else {
            this.level = level;
        }
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String toString() {
        return level;
    }
}
