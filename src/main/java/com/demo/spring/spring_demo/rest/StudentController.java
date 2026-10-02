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
import com.demo.spring.spring_demo.domain.Course;
import com.demo.spring.spring_demo.domain.Enrollment;
import com.demo.spring.spring_demo.domain.School;
import com.demo.spring.spring_demo.domain.Student;
import com.demo.spring.spring_demo.dto.EnrollmentWriteDto;
import com.demo.spring.spring_demo.dto.StudentWriteDto;
import com.demo.spring.spring_demo.service.StudentService;

import io.micrometer.core.ipc.http.HttpSender.Response;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;




@RestController
@RequestMapping("/api/students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    /*
        ANTI-PATTERNS - DONT; DO STUFF LIKE THIS
        http://localhost:8080/students/getstudent
        http://localhost:8080/students/deletestudent
        http://localhost:8080/students/updatestudent
    */

    // GET localhost:8080/api/students
    @GetMapping({"", "/"})
    public List<Student> getAllStudents(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int count,
        @RequestParam(defaultValue = "true") boolean asc
    ) {
        return studentService.getAllStudents(page, count, asc);
    }

    // @PostMapping
    // public ResponseEntity<Student> insertStudent(@Valid @RequestBody Student student) {
        
    //     Student savedStudent = studentService.insertStudent(student);
        
    //     return ResponseEntity
    //         .created(
    //             ServletUriComponentsBuilder
    //                 .fromCurrentRequest()
    //                 .path("/{id}")
    //                 .buildAndExpand(savedStudent.getId())
    //                 .toUri()
    //         )
    //         .body(savedStudent);
    // }

    @PostMapping
    public ResponseEntity<Student> insertStudent(@Valid @RequestBody StudentWriteDto student) {
        
        Student savedStudent = studentService.insertStudent(student);
        
        return ResponseEntity
            .created(
                ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedStudent.getId())
                    .toUri()
            )
            .body(savedStudent);
    }

    @PostMapping({"/{studentId}/enrollments"})
    public ResponseEntity<Enrollment> insertEnrollment(@Valid @RequestBody EnrollmentWriteDto enrollment) {
        
        Enrollment savedEnrollment = studentService.insertEnrollment(enrollment);
        
        return ResponseEntity
            .created(
                ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedEnrollment.getId())
                    .toUri()
            )
            .body(savedEnrollment);
    }

    @PostMapping("/courses")
    public ResponseEntity<Course> insertCourse(@Valid @RequestBody Course course) {
        Course savedCourse = studentService.insertCourse(course);

        return ResponseEntity
            .created(
                ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedCourse.getId())
                    .toUri()
            )
            .body(savedCourse);
    }

    // route parameter - typically used for resource location
    // GET http"//localhost:8080/api/students/3

    // query parameter - typically used for filtering, searching
    // GET http://localhost:8080/api/students?lastName=wilson&grade=b
    @GetMapping("/{id}")
    public Student getStudentById(@PathVariable int id) {
        return studentService.getStudentById(id);
    }

    // GET http://localhost:8080/api/students?lastName=wilson
    @GetMapping(params = "lastName")
    public List<Student> getStudentByLastName(@RequestParam String lastName) {
        return studentService.getStudentByLastName(lastName);
    }

    // GET http://localhost:8080/api/students?firstName=hi
    @GetMapping(params = "firstName")
    public List<Student> getStudentByFirstName(@RequestParam String firstName) {
        return studentService.getStudentByFirstName(firstName);
    }

    @DeleteMapping("/(id)")
    public ResponseEntity<Void> deleteStudent(@PathVariable int id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable int id, @Valid @RequestBody Student student) {
        return studentService.updateStudent(id, student);
    }

    @GetMapping(params = "school")
    public List<Student> getStudentsBySchoolName(@RequestParam String school) {
        return studentService.findStudentsBySchoolName(school);
    }
    
}
