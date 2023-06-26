package de.neuefische.frontendstudentdb.student;

public record StudentWithoutIdAndMatriculationNumber(
        String name,
        Integer age,
        String course
) {
}
