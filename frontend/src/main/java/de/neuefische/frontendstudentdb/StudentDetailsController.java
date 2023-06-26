package de.neuefische.frontendstudentdb;

import de.neuefische.frontendstudentdb.student.Student;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class StudentDetailsController implements Initializable {

    @FXML
    private Label name;
    @FXML
    private Label course;
    @FXML
    private Label matriculationNumber;
    @FXML
    private Label age;

    private Student selectedStudent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (selectedStudent != null) {
            name.setText(selectedStudent.getName());
            course.setText(selectedStudent.getCourse());
            matriculationNumber.setText(selectedStudent.getMatriculationNumber());
            age.setText(String.valueOf(selectedStudent.getAge()));
        }
    }

    public void initializeStudentDetails(Student student) {
        selectedStudent = student;
    }
}
