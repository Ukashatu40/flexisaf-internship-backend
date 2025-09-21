package com.flexisaf.student.controller;

import com.flexisaf.student.assembler.StudentModelAssembler;
import com.flexisaf.student.model.Student;
import com.flexisaf.student.service.StudentService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@RestController
@RequestMapping("/students")
public class StudentController {
    private final StudentService service;
    private final StudentModelAssembler assembler;

    public StudentController(StudentService service, StudentModelAssembler assembler) {
        this.service = service;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Student>> all() {
        List<EntityModel<Student>> students = service.getAllStudents().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(students,
                linkTo(methodOn(StudentController.class).all()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Student> one(@PathVariable Long id) {
        Student student = service.getStudentById(id);
        return assembler.toModel(student);
    }

    @PostMapping
    public ResponseEntity<EntityModel<Student>> newStudent(@RequestBody Student student) {
        EntityModel<Student> entityModel = assembler.toModel(service.createStudent(student));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @PutMapping("/{id}")
public ResponseEntity<EntityModel<Student>> replaceStudent(@RequestBody Student newStudent, @PathVariable Long id) {
    EntityModel<Student> entityModel = assembler.toModel(service.updateStudent(newStudent, id));
    return ResponseEntity.ok().body(entityModel);
}

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        service.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}