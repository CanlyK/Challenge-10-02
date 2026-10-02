package com.demo.spring.spring_demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.spring.spring_demo.domain.School;

public interface SchoolRepository extends JpaRepository<School, Integer> {

}
