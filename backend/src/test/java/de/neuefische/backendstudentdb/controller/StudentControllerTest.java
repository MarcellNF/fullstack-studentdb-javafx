package de.neuefische.backendstudentdb.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.neuefische.backendstudentdb.model.Student;
import de.neuefische.backendstudentdb.model.StudentWithoutIdAndMatriculationNumber;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Nested
    class ListStudentTests {

        @Test
        @DirtiesContext
        void listStudents_whenCollectionEmpty_ReturnStatusOkAndEmptyList() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/students"))
                    .andExpect(status().isOk())
                    .andExpect(content().json("[]"));
        }

        @Test
        @DirtiesContext
        void listStudents_whenCollectionNotEmpty_ReturnStatusOkAndListOfStudents() throws Exception {
            Student student = new Student("1", "123456", "Frank", 22, "Computer Science");
            Student student2 = new Student("2", "123457", "Anna", 23, "Computer Science");
            Student student3 = new Student("3", "123458", "Max", 24, "Computer Science");
            List<Student> students = List.of(student, student2, student3);

            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/api/students/add-students")
                            .contentType("application/json")
                            .content(objectMapper.writeValueAsString(students)))
                    .andExpect(status().isCreated())
                    .andReturn();

            List<Student> actual = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {
            });

            mockMvc.perform(MockMvcRequestBuilders.get("/api/students"))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(actual)));
        }

    }

    @Nested
    class OtherTests {
        private StudentWithoutIdAndMatriculationNumber studentWithoutIdAndMatriculationNumber;
        List<Student> students;
        MvcResult result;
        Student actual;

        private void prepareData() throws Exception {
            Student student = new Student("1", "123456", "Frank", 22, "Computer Science");
            Student student2 = new Student("2", "123457", "Anna", 23, "Computer Science");
            Student student3 = new Student("3", "123458", "Max", 24, "Computer Science");
            studentWithoutIdAndMatriculationNumber = new StudentWithoutIdAndMatriculationNumber("Frank", 22, "Computer Science");
            students = List.of(student, student2, student3);
            result = mockMvc.perform(MockMvcRequestBuilders.post("/api/students")
                            .contentType("application/json")
                            .content(objectMapper.writeValueAsString(studentWithoutIdAndMatriculationNumber)))
                    .andExpect(status().isCreated())
                    .andReturn();
            actual = objectMapper.readValue(result.getResponse().getContentAsString(), Student.class);
        }

        @BeforeEach
        public void setup() throws Exception {
            prepareData();
        }

        @Test
        @DirtiesContext
        void getStudentById_whenStudentExist_thenReturnStatusOkAndStudent() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/api/students/" + actual.id()))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(actual)));
        }

        @Test
        @DirtiesContext
        void updateStudent_whenStudentExist_thenReturnStatusOkAndUpdatedStudent() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.put("/api/students/" + actual.id())
                            .contentType("application/json")
                            .content(objectMapper.writeValueAsString(studentWithoutIdAndMatriculationNumber)))
                    .andExpect(status().isOk())
                    .andExpect(content().json(objectMapper.writeValueAsString(actual)));
        }

        @Test
        @DirtiesContext
        void deleteStudent_whenStudentExist_thenReturnStatusNoContentAndDeletedStudent() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/students/" + actual.id()))
                    .andExpect(status().isNoContent());
        }
    }
}
