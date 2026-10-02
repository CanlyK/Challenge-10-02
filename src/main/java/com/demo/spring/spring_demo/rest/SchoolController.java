package com.demo.spring.spring_demo.rest;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.demo.spring.spring_demo.dao.StudentDAO;
import com.demo.spring.spring_demo.domain.School;
import com.demo.spring.spring_demo.domain.Student;
import com.demo.spring.spring_demo.service.StudentService;

import io.micrometer.core.ipc.http.HttpSender.Response;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/schools")
public class SchoolController {
    private final StudentService studentService;
    public SchoolController(StudentService studentService) {
        this.studentService = studentService;
    }
   
    @PostMapping
    public ResponseEntity<School> insertSchool(@Valid @RequestBody School school) {
        
        School savedSchool = studentService.insertSchool(school);
        
        return ResponseEntity
            .created(
                ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedSchool.getId())
                    .toUri()    
            )
            .body(savedSchool);
    }
   
    @GetMapping("/{id}")
    public School getSchoolById(@PathVariable int id) {
        return studentService.getSchoolById(id);
    }
}
