package com.example.StudentManagementApplication.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.StudentManagementApplication.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}