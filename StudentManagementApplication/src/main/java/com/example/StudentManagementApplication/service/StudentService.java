package com.example.StudentManagementApplication.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.StudentManagementApplication.dto.StudentDTO;
import com.example.StudentManagementApplication.entity.Student;
import com.example.StudentManagementApplication.exception.StudentNotFoundException;
import com.example.StudentManagementApplication.repository.StudentRepository;

@Service
public class StudentService {
    private static final Logger logger =
            LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private StudentRepository repo;

    // SAVE STUDENT
    public StudentDTO saveStudent(StudentDTO dto) {

        logger.info("Saving student with id {}", dto.getId());

        Student s = new Student();
//        s.setId(dto.getId());
        s.setName(dto.getName());
        s.setCourse(dto.getCourse());

        Student saved = repo.save(s);

        logger.info("Student saved successfully");

        return mapToDTO(saved);
    }

    // GET ALL STUDENTS
    public List<StudentDTO> getAllStudents() {

        List<Student> list = repo.findAll();

        return list.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    // GET STUDENT BY ID
    public StudentDTO getStudentById(int id) {

        logger.info("Fetching student with id {}", id);

        Student s = repo.findById(id)
                .orElseThrow(() -> {
                    logger.error("Student not found with id {}", id);
                    return new StudentNotFoundException("Student Not Found With Id : " + id);
                });

        return mapToDTO(s);
    }

    // UPDATE STUDENT
    public StudentDTO updateStudent(StudentDTO dto) {

        if (!repo.existsById(dto.getId())) {
            throw new StudentNotFoundException("Student Not Found With Id : " + dto.getId());
        }

        Student s = new Student();
        s.setId(dto.getId());
        s.setName(dto.getName());
        s.setCourse(dto.getCourse());

        Student updated = repo.save(s);

        return mapToDTO(updated);
    }

    // DELETE STUDENT
    public String deleteStudent(int id) {

        if (!repo.existsById(id)) {
            throw new StudentNotFoundException("Student Not Found With Id : " + id);
        }

        repo.deleteById(id);

        return "Student Deleted Successfully";
    }

    // ⭐ COMMON MAPPING METHOD (VERY IMPORTANT INDUSTRY PRACTICE)
    private StudentDTO mapToDTO(Student s) {

        StudentDTO dto = new StudentDTO();
        dto.setId(s.getId());
        dto.setName(s.getName());
        dto.setCourse(s.getCourse());

        return dto;
    }
}