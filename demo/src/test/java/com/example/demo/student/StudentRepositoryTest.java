package com.example.demo.student;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Transactional
class StudentRepositoryTest {

    @Autowired
    private StudentRepository underTest;

    @Test
    void itShouldfindStudentByEmail() {
        //given
        Student student = new Student(
                "Srinidhi1",
                "sri1@gmail.com",
                LocalDate.of(1999, Month.JANUARY, 24)
        );
        underTest.save(student);

        Optional<Student> stu = underTest.findStudentByEmail(student.getEmail());
        boolean exists = stu.isPresent();

        assertThat(exists).isFalse();
    }
}