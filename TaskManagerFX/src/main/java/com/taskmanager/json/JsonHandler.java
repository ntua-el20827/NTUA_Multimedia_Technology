// JSONHandler.java
import com.taskmanager.model.Task;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class JsonHandler {
    private static final String TASKS_FILE_PATH = "resources/medialab/tasks.json";

    // Αποθήκευση λίστας Task σε JSON
    public static void saveTasks(List<Task> tasks) {
        JSONArray jsonArray = new JSONArray();

        for (Task task : tasks) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", task.getTitle());
            jsonObject.put("description", task.getDescription());
            jsonObject.put("category", task.getCategory());
            jsonObject.put("priority", task.getPriority());
            jsonObject.put("dueDate", task.getDueDate());  // Προσοχή στη μορφοποίηση της ημερομηνίας
            jsonObject.put("status", task.getStatus());
            jsonArray.add(jsonObject);
        }

        try (FileWriter writer = new FileWriter(TASKS_FILE_PATH)) {
            writer.write(jsonArray.toJSONString()); // JSON σε string
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Φόρτωση λίστας Task από JSON
    public static List<Task> loadTasks() {
        List<Task> tasks = new ArrayList<>();

        try (FileReader reader = new FileReader(TASKS_FILE_PATH)) {
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = (JSONArray) jsonParser.parse(reader);

            for (Object obj : jsonArray) {
                JSONObject jsonObject = (JSONObject) obj;

                String title = (String) jsonObject.get("title");
                String description = (String) jsonObject.get("description");
                String category = (String) jsonObject.get("category");
                String priority = (String) jsonObject.get("priority");
                //String dueDate = (String) jsonObject.get("dueDate");
                String status = (String) jsonObject.get("status");
                LocalDate dueDate = LocalDate.of(2020, Month.JANUARY, 8);

                // Δημιουργία αντικειμένου Task από τα δεδομένα JSON
                Task task = new Task(title, description, category, priority, dueDate, status);
                tasks.add(task);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return tasks;
    }
}
