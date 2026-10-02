package com.demo.spring.spring_demo.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.demo.spring.spring_demo.dao.CourseRepository;
import com.demo.spring.spring_demo.dao.EnrollmentRepository;
import com.demo.spring.spring_demo.dao.SchoolRepository;
import com.demo.spring.spring_demo.dao.StudentDAO;
import com.demo.spring.spring_demo.dao.StudentRepository;
import com.demo.spring.spring_demo.domain.Course;
import com.demo.spring.spring_demo.domain.Enrollment;
import com.demo.spring.spring_demo.domain.School;
import com.demo.spring.spring_demo.domain.Student;
import com.demo.spring.spring_demo.dto.EnrollmentWriteDto;
import com.demo.spring.spring_demo.dto.StudentWriteDto;
import com.demo.spring.spring_demo.exceptions.RecordNotFoundException;

import org.springframework.transaction.annotation.Transactional;

@Service 
public class StudentService {
    // private final StudentDAO studentDAO;
    private final StudentRepository studentRepo;
    private final EnrollmentRepository enrollmentRepo;
    private final CourseRepository courseRepo;
    private final SchoolRepository schoolRepo;
    private static final Logger log = LoggerFactory.getLogger(StudentService.class);

    public StudentService(
        // @Qualifier("entityManagerStudentDAO") StudentDAO studentDAO, 
        StudentRepository studentRepository,
        EnrollmentRepository enrollmentRepository,
        CourseRepository courseRepository,
        SchoolRepository schoolRepository
    ) {
        // this.studentDAO = studentDAO;
        this.studentRepo = studentRepository;
        this.enrollmentRepo = enrollmentRepository;
        this.courseRepo = courseRepository;
        this.schoolRepo = schoolRepository;
    }

    @Transactional
    public Student insertStudent(StudentWriteDto student) {
        // return studentDAO.insert(student);
        School school = schoolRepo.findById(student.getSchoolId())
            .orElse(null);

        Student newStudent = new Student(
            student.getFirstName(),
            student.getLastName(),
            student.getEmail(),
            school
        );

        return studentRepo.save(newStudent);
    }

    @Transactional
    public School insertSchool(School school) {
        return schoolRepo.save(school);
    }

    @Transactional
    public Course insertCourse(Course course) {
        return courseRepo.save(course);
    }

    @Transactional
    public Enrollment insertEnrollment(EnrollmentWriteDto enrollment) {
        Course course = courseRepo.findById(enrollment.getCourseId())
            .orElse(null);
        Student student = studentRepo.findById(enrollment.getStudentId())
        .orElse(null);

        Enrollment newEnrollment = new Enrollment(
            student,
            course,
            enrollment.getEnrollmentDate(),
            enrollment.getGrade()
        );

        return enrollmentRepo.save(newEnrollment);
    }

    @Transactional
    public List<Student> findStudentsBySchoolName(String name) {
        return studentRepo.findBySchool_Name(name);
    }

    @Transactional(readOnly = true)
    public List<Student> getAllStudents(int page, int count, boolean asc) {
        Sort.Direction direction = asc
        ? Sort.Direction.ASC
        : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(
            page,
            count,
            Sort.by(direction, "id")
        );
        Page<Student> students = studentRepo.findAll(pageable);
        return students.getContent();
    }

    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        // return studentDAO.findAll();
        return studentRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Student getStudentById(int id) {
        // Student student = studentDAO.findById(id);
        Student student = studentRepo.findById(id)
            .orElseThrow(() -> 
                new RecordNotFoundException(
                    "Student not found with id: " + id
                )
            );
        // if (student == null) {
        //     throw new RecordNotFoundException("Student not found with id: " + id);
        // }

        return student;
    }

    @Transactional
    public void deleteStudent(int id) {
        Student student = getStudentById(id);
        // studentDAO.delete(student);
        studentRepo.delete(student);
    }

    @Transactional(readOnly = true)
    public List<Student> getStudentByLastName(String lastName) {
        // return studentDAO.findByLastName(lastName);
        return studentRepo.findByLastName(lastName);
    }

    @Transactional(readOnly = true)
    public List<Student> getStudentByFirstName(String firstName) {
        return studentRepo.findByFirstName(firstName);
    }

    @Transactional
    public Student updateStudent(int id, Student student) {
        Student existingStudent = getStudentById(id);
        
        existingStudent.setEmail(student.getEmail());
        existingStudent.setFirstName(student.getFirstName());
        existingStudent.setLastName(student.getLastName());
        
        return existingStudent;
    }

    @Transactional(readOnly = true)
    public School getSchoolById(int id) {
        School school = schoolRepo.findById(id)
            .orElseThrow(() -> 
                new RecordNotFoundException(
                    "School not found with id: " + id
                )
            );

        return school;
    }
}
