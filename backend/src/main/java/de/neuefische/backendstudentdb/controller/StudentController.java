package de.neuefische.backendstudentdb.controller;

import de.neuefische.backendstudentdb.model.Student;
import de.neuefische.backendstudentdb.model.StudentWithoutIdAndMatriculationNumber;
import de.neuefische.backendstudentdb.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public List<Student> listStudents() {
        return studentService.listStudents();
    }

    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable String id) {
        return studentService.getStudentById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Student addStudent(@RequestBody StudentWithoutIdAndMatriculationNumber student) {
        return studentService.addStudent(student);
    }

    @PostMapping("/add-students")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Student> addStudents(@RequestBody List<StudentWithoutIdAndMatriculationNumber> students) {
        return studentService.addStudents(students);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable String id, @RequestBody StudentWithoutIdAndMatriculationNumber student) {
        return studentService.updateStudent(id, student);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable String id) {
        studentService.deleteStudent(id);
    }

}
