package com.taskmanager.ui;

import com.taskmanager.model.Category;
import com.taskmanager.json.JSONHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

public class CategoryController {
    @FXML
    private TextField categoryNameField;
    @FXML
    private Button deleteButton;

    private Category currentCategory;
    private boolean categoryDeleted = false;

    @FXML
    private void initialize() {
        deleteButton.setVisible(false);
    }

    public void setCategory(Category category) {
        if (category != null) {
            this.currentCategory = category;
            categoryNameField.setText(category.getName());
            deleteButton.setVisible(true);
        }
    }

    public Category getCategory() {
        return categoryDeleted ? null : currentCategory;
    }

    @FXML
    private void onSaveCategory() {
        if (categoryNameField.getText().isEmpty()) {
            showAlert("Validation Error", "Category name cannot be empty.");
            return;
        }

        if (currentCategory == null) { // New category
            currentCategory = new Category(categoryNameField.getText());
        }else { // Επεξεργασία υπάρχουσας
            currentCategory.setName(categoryNameField.getText());
        }
        closeWindow();
    }

    @FXML
    private void onDeleteCategory() {
        categoryDeleted = true;
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) categoryNameField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
