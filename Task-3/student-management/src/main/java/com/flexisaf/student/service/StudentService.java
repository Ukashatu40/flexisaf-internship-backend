package com.flexisaf.student.service;

import com.flexisaf.student.model.Student;
import com.flexisaf.student.model.StudentNotFoundException;
import com.flexisaf.student.repository.StudentRepository;
import java.util.List;
import org.springframework.stereotype.Service;


@Service
public class StudentService {
    private final StudentRepository repository;

    public StudentService(StudentRepository repository) {
        this.repository = repository;
    }

    public List<Student> getAllStudents() {
        return repository.findAll();
    }

    public Student getStudentById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    public Student createStudent(Student student) {
        return repository.save(student);
    }

    public Student updateStudent(Student student, Long id) {
        Student existingStudent = getStudentById(id);
        existingStudent.setName(student.getName());
        existingStudent.setDepartment(student.getDepartment());
        return repository.save(existingStudent);
    }

    public void deleteStudent(Long id) {
        if (!repository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        repository.deleteById(id);
    }
}