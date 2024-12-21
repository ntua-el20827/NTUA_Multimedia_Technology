package com.taskmanager.model;

public class Category {
    private String name;

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true; // Αν είναι το ίδιο αντικείμενο
        if (obj == null || getClass() != obj.getClass()) return false; // Αν είναι διαφορετικός τύπος

        Category category = (Category) obj;
        return name != null && name.equals(category.name); // Σύγκριση με βάση το όνομα
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    public int compareTo(Category category) {
        return name.compareTo(category.getName());
    }
}
