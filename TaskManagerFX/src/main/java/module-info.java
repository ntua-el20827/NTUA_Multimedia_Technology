module com.taskmanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    //requires json.simple;
    //requires com.fasterxml.jackson.databind;  // Για ανάγνωση/εγγραφή JSON

    // Opens:
    opens com.taskmanager.ui to javafx.fxml;
    opens com.taskmanager.model to com.google.gson;

    // Exports
    exports com.taskmanager;
    exports com.taskmanager.model;
    //exports com.taskmanager.json;
    exports com.taskmanager.ui;  // Αν η GUI σου βρίσκεται εδώ
}
