package de.neuefische.backendstudentdb.service;

import de.neuefische.backendstudentdb.model.Student;
import de.neuefische.backendstudentdb.model.StudentWithoutIdAndMatriculationNumber;
import de.neuefische.backendstudentdb.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final MatriculationService matriculationService;

    public List<Student> listStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(String id) {
        return studentRepository
                .findById(id)
                .orElseThrow(() -> new NoSuchElementException("Student with id " + id + " not found"));
    }

    public Student addStudent(StudentWithoutIdAndMatriculationNumber student) {
        return studentRepository.save(
                Student.generateStudentWithoutIdAndMatriculationNumber(
                        student,
                        matriculationService.generateMatriculationNumber()
                )
        );
    }

    public List<Student> addStudents(List<StudentWithoutIdAndMatriculationNumber> students) {
        return studentRepository.saveAll(students.stream().map(student -> Student.generateStudentWithoutIdAndMatriculationNumber(
                student,
                matriculationService.generateMatriculationNumber()
        )).toList());
    }

    public Student updateStudent(String id, StudentWithoutIdAndMatriculationNumber student) {
        Student studentToUpdate = getStudentById(id);
        return studentRepository.save(new Student(
                studentToUpdate.id(),
                studentToUpdate.matriculationNumber(),
                student.name(),
                student.age(),
                student.course()
        ));
    }

    public void deleteStudent(String id) {
        Student studentToDelete = getStudentById(id);
        studentRepository.delete(studentToDelete);
    }

}
