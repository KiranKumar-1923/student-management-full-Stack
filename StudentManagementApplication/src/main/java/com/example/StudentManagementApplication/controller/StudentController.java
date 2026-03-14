package com.example.StudentManagementApplication.controller;

import java.util.List;

import com.example.StudentManagementApplication.dto.StudentDTO;
import com.example.StudentManagementApplication.response.ApiResponse;
import com.example.StudentManagementApplication.service.StudentService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
//@CrossOrigin("*")
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {

    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<StudentDTO>> saveStudent(@Valid @RequestBody StudentDTO dto) {

        StudentDTO saved = service.saveStudent(dto);

        ApiResponse<StudentDTO> response =
                new ApiResponse<>(201, "Student Created Successfully", saved);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<StudentDTO>>> getAllStudents() {

        List<StudentDTO> list = service.getAllStudents();

        ApiResponse<List<StudentDTO>> response =
                new ApiResponse<>(200, "Students Fetched Successfully", list);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<StudentDTO>> getStudent(@PathVariable int id) {

        StudentDTO dto = service.getStudentById(id);

        ApiResponse<StudentDTO> response =
                new ApiResponse<>(200, "Student Found", dto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ApiResponse<StudentDTO>> updateStudent(@Valid @RequestBody StudentDTO dto) {

        StudentDTO updated = service.updateStudent(dto);

        ApiResponse<StudentDTO> response =
                new ApiResponse<>(200, "Student Updated Successfully", updated);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteStudent(@PathVariable int id) {

        String msg = service.deleteStudent(id);

        ApiResponse<String> response =
                new ApiResponse<>(200, msg, null);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}