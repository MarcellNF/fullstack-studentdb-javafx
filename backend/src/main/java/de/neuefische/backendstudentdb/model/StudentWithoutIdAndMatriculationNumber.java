package de.neuefische.backendstudentdb.model;

public record StudentWithoutIdAndMatriculationNumber(
        String name,
        Integer age,
        String course
) {
}
