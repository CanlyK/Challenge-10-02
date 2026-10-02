package com.demo.spring.spring_demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.spring.spring_demo.domain.Enrollment;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Integer> {

}
