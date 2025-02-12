# NTUA_Multimedia_Technology
NTUA ECE MT 2024

### About TaskManager
This project is a Task Management System designed to introduce students to Java programming and its real-world applications. It focuses on object-oriented design, data persistence using JSON, and GUI development with JavaFX. The system allows users to manage tasks, categories, priorities, and reminders while ensuring data is stored and retrieved efficiently. Through this project, students gain hands-on experience in building structured, interactive, and persistent Java applications

### Screenshots
![image](https://github.com/user-attachments/assets/4232135f-59bd-458c-99a3-aa5eb430ee6d)
![image](https://github.com/user-attachments/assets/9aec682c-b1fc-4700-897f-5b9a34ff32c0)
![image](https://github.com/user-attachments/assets/8ffa3c8e-ed9c-40fc-9730-48e9b9f2e4d9)
![image](https://github.com/user-attachments/assets/a104d4f7-12d4-4d14-99b8-0b1d399c8136)
![image](https://github.com/user-attachments/assets/61432b10-086a-4bac-ae34-77f1bf39238e)

### Dependencies
1. Java openjdk 17.0.14
2. JavaFX 17.0.6
3. Gradle Latest or Wrapper Provided (Required for building the project)
4. Gson 2.10.1  (JSON parsing library for data serialization and deserialization)

### Set up and RUN
1. Clone the Repository
2. Navigate to prject directory and run these commands:
```shell
./gradlew clean build
/gradlew run --args="--module-path </path/to/javafx/lib> --add-modules javafx.controls,javafx.fxml"
```
### Project Overview
This project is built using Gradle and follows the structure below:
project-root/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── taskmanager/
│   │   │           ├── json/
│   │   │           │   ├── JSONHandler.java   # Handles reading/writing JSON data
│   │   │           │   └── LocalDateAdapter.java  # Adapts LocalDate serialization
│   │   │           ├── model/
│   │   │           │   ├── Task.java           # Core entity representing tasks
│   │   │           │   ├── Category.java       # Represents a category for tasks
│   │   │           │   ├── PriorityLevel.java  # Defines priority levels
│   │   │           │   ├── Reminder.java       # Manages task reminders
│   │   │           │   ├── ReminderType.java   # Enumerates reminder types
│   │   │           │   ├── StatusType.java     # Defines task status options
│   │   │           ├── ui/
│   │   │           │   ├── MainController.java     # Manages main UI interactions
│   │   │           │   ├── TaskController.java     # Handles task creation/editing
│   │   │           │   ├── CategoryController.java # Handles category management
│   │   │           │   ├── PriorityController.java # Manages priority levels
│   │   │           │   ├── ReminderController.java # Manages task reminders
│   │   │           └── MainApp.java  # Application entry point (initializes JSON data, loads UI)
│   │   │   
│   │   ├── resources/
│   │   │   ├── medialab/
│   │   │   │   ├── tasks.json       # Stores task data
│   │   │   │   ├── categories.json  # Stores category data
│   │   │   │   ├── priorities.json  # Stores priority level data
│   │   │   │   └── reminders.json   # Stores reminder data
│   │   │   └── views/
│   │   │       ├── main.fxml               # Main UI layout
│   │   │       ├── task_management.fxml    # Task management window
│   │   │       ├── category_management.fxml # Category management window
│   │   │       ├── priority_management.fxml # Priority level management window
│   │   │       ├── reminder_management.fxml # Reminder management window
│   │
│   └── module-info.java  # Java module system configuration
│
├── gradle/  # Gradle build scripts
├── build.gradle  # Gradle build configuration
├── .gitignore  # Git ignored files
├── doc/  # Documentation and Javadocs

- Object-Oriented Design: Implements core classes (Task, Category, PriorityLevel, Reminder) with has-a relationships.
- Data Persistence: Uses JSONHandler to read/write JSON files, ensuring data is saved across sessions.
- GUI (JavaFX): The UI is implemented with JavaFX FXML, and Scene Builder is used for design.
- Controllers: MainController connects UI elements to backend logic, while subcontrollers manage individual components.
