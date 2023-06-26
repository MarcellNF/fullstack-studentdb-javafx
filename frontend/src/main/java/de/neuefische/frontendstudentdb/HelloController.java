package de.neuefische.frontendstudentdb;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.neuefische.frontendstudentdb.student.Student;
import de.neuefische.frontendstudentdb.student.StudentService;
import de.neuefische.frontendstudentdb.student.StudentWithoutIdAndMatriculationNumber;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.net.http.HttpClient;
import java.util.List;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    HttpClient client = HttpClient.newHttpClient();
    ObjectMapper objectMapper = new ObjectMapper();

    private final StudentService studentService = new StudentService(client, objectMapper);

    @FXML
    private ListView<Student> studentListView;
    @FXML
    private TextField studentName;
    @FXML
    private TextField studentCourse;
    @FXML
    private TextField studentAge;
    @FXML
    private Button addStudentButton;

    private ObservableList<Student> studentObservableList = FXCollections.observableArrayList();

    @FXML
    private void deleteStudent() {
        Student selectedStudent = studentListView.getSelectionModel().getSelectedItem();
        studentService.deleteStudent(selectedStudent.getId());
        studentObservableList.remove(selectedStudent);
    }

    @FXML
    private void addStudent() {
        StudentWithoutIdAndMatriculationNumber student = new StudentWithoutIdAndMatriculationNumber(
                studentName.getText(),
                Integer.parseInt(studentAge.getText()),
                studentCourse.getText()
        );
        Student savedStudent = studentService.addStudent(student);
        studentObservableList.add(savedStudent);
    }

    private void openStudentDetailsWindow(Student student) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("student-details-view.fxml"));
            Parent root = loader.load();

            StudentDetailsController detailsController = loader.getController();
            detailsController.initializeStudentDetails(student); // Methode zum Übergeben der Studentendaten

            Stage detailsStage = new Stage();
            detailsStage.setTitle("Student Details");
            detailsStage.setScene(new Scene(root));
            detailsStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Student> students = studentService.getAllStudents();
        studentObservableList.addAll(students);
        studentListView.setItems(studentObservableList);

        studentListView.setCellFactory(studentListView -> new ListCell<Student>() {
            @Override
            protected void updateItem(Student student, boolean empty) {
                super.updateItem(student, empty);
                if (student != null) {
                    setText(student.getName());
                } else {
                    setText(null);
                }
            }
        });

        studentListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Doppelklick erkannt
                Student selectedStudent = studentListView.getSelectionModel().getSelectedItem();
                if (selectedStudent != null) {
                    openStudentDetailsWindow(selectedStudent);
                }
            }
        });
    }
}