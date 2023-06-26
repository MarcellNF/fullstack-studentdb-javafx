package de.neuefische.frontendstudentdb.student;


import java.util.Objects;

public class Student {

    private String id;
    private String matriculationNumber;
    private String name;
    private Integer age;
    private String course;

    public Student(String id, String matriculationNumber, String name, Integer age, String course) {
        this.id = id;
        this.matriculationNumber = matriculationNumber;
        this.name = name;
        this.age = age;
        this.course = course;
    }

    public Student() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMatriculationNumber() {
        return matriculationNumber;
    }

    public void setMatriculationNumber(String matriculationNumber) {
        this.matriculationNumber = matriculationNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id) && Objects.equals(matriculationNumber, student.matriculationNumber) && Objects.equals(name, student.name) && Objects.equals(age, student.age) && Objects.equals(course, student.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, matriculationNumber, name, age, course);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", matriculationNumber='" + matriculationNumber + '\'' +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", course='" + course + '\'' +
                '}';
    }
}
