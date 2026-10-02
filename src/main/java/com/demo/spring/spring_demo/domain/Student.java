package com.demo.spring.spring_demo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private School school;

    @OneToMany(
        mappedBy = "student",
        orphanRemoval = true,
        cascade = CascadeType.ALL
    )
    @JsonIgnore
    private List<Enrollment> enrollments = new ArrayList<>();

    @Size(max = 20)
    @Column(name="first_name", length = 45)
    private String firstname;

    @Size(max = 20)
    @Column(name="last_name", length = 45)
    private String lastname;

    // NotNull
    // NotBlank
    // NotEmpty
    // @Size for strings
    // @Min
    // @Max for numbers
    // @Positive
    // @PositiveOrZero
    // @Email
    // @Pattern
    // by default nullable is true
    @Email
    @NotBlank //ensure not null, emptly, or just whitespace, applies to java applciation itself, nullable is database level constraint
    @Column(name="email", length = 45, nullable = false, unique = true)
    private String email;

    public Student(String firstname, String lastname, @Email @NotBlank String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public Student(String firstname, String lastname, @Email @NotBlank String email, School school) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.school = school;
    }

    // required for hibernate, a default constructor
    public Student() {}

    public Integer getId() {
        return id;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public School getSchool() {
        return this.school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public void addEnrollment(Enrollment enrollment) {
        enrollments.add(enrollment);
        enrollment.setStudent(this);
    }

    public void removeEnrollment(Enrollment enrollment) {
        enrollments.remove(enrollment);
        enrollment.setStudent(null);
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }
}
