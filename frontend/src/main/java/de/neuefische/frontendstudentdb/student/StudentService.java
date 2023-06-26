package de.neuefische.frontendstudentdb.student;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class StudentService {

    private final HttpClient client;
    private final ObjectMapper objectMapper;
    private static final URI STUDENT_API_URI = URI.create("http://localhost:8080/api/students");
    private static final int HTTP_NO_CONTENT = 204;

    public StudentService(HttpClient client, ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    public List<Student> getAllStudents() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(STUDENT_API_URI)
                .header("Accept", "application/json")
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(responseBody -> mapToStudentList(responseBody, objectMapper))
                .join();
    }

    public Student getStudentById(String id) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/students/" + id))
                .header("Accept", "application/json")
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenApply(responseBody -> mapToStudent(responseBody, objectMapper))
                .join();
    }

    public Student addStudent(StudentWithoutIdAndMatriculationNumber student) {
        try {
            String requestBody = objectMapper.writeValueAsString(student);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(STUDENT_API_URI)
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(responseBody -> mapToStudent(responseBody, objectMapper))
                    .join();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Student updateStudent(String id, StudentWithoutIdAndMatriculationNumber student) {
        try {
            String requestBody = objectMapper.writeValueAsString(student);
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(STUDENT_API_URI.resolve(id))
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .PUT(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                    .thenApply(HttpResponse::body)
                    .thenApply(responseBody -> mapToStudent(responseBody, objectMapper))
                    .join();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteStudent(String id) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/students/" + id))
                .DELETE()
                .build();

        return client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::statusCode)
                .thenApply(statusCode -> statusCode == HTTP_NO_CONTENT)
                .join();
    }

    private Student mapToStudent(String responseBody, ObjectMapper objectMapper) {
        try {
            return objectMapper.readValue(responseBody, Student.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    static List<Student> mapToStudentList(String responseBody, ObjectMapper objectMapper) {
        try {
            return objectMapper.readValue(responseBody, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
