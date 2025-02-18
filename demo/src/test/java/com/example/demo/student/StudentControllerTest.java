package com.example.demo.student;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class) // ✅ JUnit 5
public class StudentControllerTest {

    private MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter ow = objectMapper.writer();

    @Mock
    private StudentService studentService; // ✅ Mock service instead of repository

    @InjectMocks
    private StudentController studentController;

    private final Student student1 = new Student(1, "S", "s@gmail.com", LocalDate.of(1997, Month.JANUARY, 27));
    private final Student student2 = new Student(2, "A", "a@gmail.com", LocalDate.of(1997, Month.JANUARY, 27));

    @BeforeEach
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    }

    @Test
    public void getAllStudents_success() throws Exception {
        List<Student> students = List.of(student1, student2);
        Mockito.when(studentService.getStudents()).thenReturn(students); // ✅ Use studentService instead of studentRepository

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/student")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
