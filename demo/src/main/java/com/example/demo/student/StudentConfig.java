package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
        return args -> {
            Student srinidhi = new Student(
                    "Srinidhi",
                    "ss@gmail.com",
                    LocalDate.of(1999, Month.JANUARY, 24)
            );

            Student alex = new Student(
                    "Alex",
                    "alex@gmail.com",
                    LocalDate.of(1997, Month.JANUARY, 27)
            );

            studentRepository.saveAll(List.of(srinidhi,alex));
        };
    }
}
