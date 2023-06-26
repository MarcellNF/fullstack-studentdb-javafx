package de.neuefische.backendstudentdb.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "students")
public record Student(
        @MongoId
        String id,
        String matriculationNumber,
        String name,
        Integer age,
        String course
) {
        public static Student generateStudentWithoutIdAndMatriculationNumber(StudentWithoutIdAndMatriculationNumber studentWithoutIdAndMatriculationNumber, String matriculationNumber) {
                return new Student(
                        null,
                        matriculationNumber,
                        studentWithoutIdAndMatriculationNumber.name(),
                        studentWithoutIdAndMatriculationNumber.age(),
                        studentWithoutIdAndMatriculationNumber.course()
                );
        }
}
