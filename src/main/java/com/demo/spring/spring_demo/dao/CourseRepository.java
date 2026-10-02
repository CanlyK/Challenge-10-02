package com.demo.spring.spring_demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.spring.spring_demo.domain.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {

}
