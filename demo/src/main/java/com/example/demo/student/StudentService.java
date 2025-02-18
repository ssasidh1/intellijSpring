package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents(){
       return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
       Optional<Student> studentOptional =  studentRepository.findStudentByEmail(student.getEmail());
        if(studentOptional.isPresent()){
            throw new IllegalArgumentException("Student already exists");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        boolean exists = studentRepository.existsById(id);
        if(exists)studentRepository.deleteById(id);
        else throw new IllegalArgumentException("Student does not exist");

    }

    public void updateStudent(long id, String name, String email) {

        Student student = studentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Student not found"));

        if(name != null && !name.isEmpty() && !Objects.equals(student.getName(), name)) {
            student.setName(name);
        }
        if(email != null && !email.isEmpty() && !Objects.equals(student.getEmail(), email)) {
           Optional<Student> studentOptional =  studentRepository.findStudentByEmail(email);
           if(studentOptional.isEmpty())student.setName(email);
           else throw new IllegalArgumentException("Student already exists");
           student.setEmail(email);
        }
        studentRepository.save(student);

    }
}
