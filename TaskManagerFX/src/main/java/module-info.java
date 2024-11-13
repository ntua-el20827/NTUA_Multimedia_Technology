module com.taskmanager {
    requires javafx.controls;
    requires javafx.fxml;
    //requires com.fasterxml.jackson.databind;  // Για ανάγνωση/εγγραφή JSON

    opens com.taskmanager.ui to javafx.fxml;
    exports com.taskmanager;
    exports com.taskmanager.model;
    exports com.taskmanager.json;
    exports com.taskmanager.ui;  // Αν η GUI σου βρίσκεται εδώ
}
