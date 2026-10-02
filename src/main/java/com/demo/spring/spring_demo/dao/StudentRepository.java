package com.demo.spring.spring_demo.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.demo.spring.spring_demo.domain.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findByLastName(String lastName);
    List<Student> findBySchool_Name(String name);

    @Query("""
            SELECT s
            FROM Student s
            WHERE s.firstname = :firstName
            ORDER BY s.id ASC
        """)
    List<Student> findByFirstName(@Param("firstName") String firstName);
}
