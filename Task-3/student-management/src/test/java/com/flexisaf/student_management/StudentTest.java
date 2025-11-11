package com.flexisaf.student_management;

import com.flexisaf.student.model.Student;
import com.flexisaf.student.repository.StudentRepository;
import com.flexisaf.student.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StudentTest {
    @Mock
    StudentService studentService;

    @Mock
    StudentRepository studentRepository;

    @Test
    void testStudentRepository(){
        Student student = new Student("Ukashatu", "Abdullahi", "Computer Science");

        when(studentRepository.save(any(Student.class))).thenReturn(student);

        Student result = studentRepository.save(new Student("Ukasha", "Umar", "cd"));

        assertEquals("Ukashatu", result.getFirstName());
        assertEquals("Abdullahi", result.getLastName());
        assertEquals("Computer Science", result.getDepartment());

    }
}
