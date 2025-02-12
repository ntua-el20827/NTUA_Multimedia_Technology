# NTUA_Multimedia_Technology
NTUA ECE MT 2024

### About TaskManager
This project is a Task Management System designed to introduce students to Java programming and its real-world applications. It focuses on object-oriented design, data persistence using JSON, and GUI development with JavaFX. The system allows users to manage tasks, categories, priorities, and reminders while ensuring data is stored and retrieved efficiently. Through this project, students gain hands-on experience in building structured, interactive, and persistent Java applications

### Screenshots
![image](https://github.com/user-attachments/assets/4232135f-59bd-458c-99a3-aa5eb430ee6d)
![image](https://github.com/user-attachments/assets/9aec682c-b1fc-4700-897f-5b9a34ff32c0)
![image](https://github.com/user-attachments/assets/8ffa3c8e-ed9c-40fc-9730-48e9b9f2e4d9)
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
```
``` shell
/gradlew run --args="--module-path </path/to/javafx/lib> --add-modules javafx.controls,javafx.fxml"
```
### Project Overview
This project is built using Gradle and follows the structure below:
![image](https://github.com/user-attachments/assets/c36c8402-6ae9-482f-925f-82bfab4d8fe5)

- Object-Oriented Design: Implements core classes (Task, Category, PriorityLevel, Reminder) with has-a relationships.
- Data Persistence: Uses JSONHandler to read/write JSON files, ensuring data is saved across sessions.
- GUI (JavaFX): The UI is implemented with JavaFX FXML, and Scene Builder is used for design.
- Controllers: MainController connects UI elements to backend logic, while subcontrollers manage individual components.
