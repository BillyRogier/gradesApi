package com.example.gradesApi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gradesApi.dto.GradeDTO;
import com.example.gradesApi.expectations.ResourceNotFoundException;
import com.example.gradesApi.model.Course;
import com.example.gradesApi.model.Grade;
import com.example.gradesApi.model.Student;
import com.example.gradesApi.repository.CourseRepository;
import com.example.gradesApi.repository.StudentRepository;
import com.example.gradesApi.service.GradeService;

@RestController
@RequestMapping("/grades")
public class GradeController {

    @Autowired
    private GradeService gradeService;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private CourseRepository courseRepository;

    @PostMapping
    public ResponseEntity<Grade> addGrade(@RequestBody GradeDTO gradeDTO) {
        Grade grade = new Grade();
        Student student = studentRepository.findById(gradeDTO.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        Course course = courseRepository.findById(gradeDTO.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        grade.setStudent(student);
        grade.setCourse(course);
        grade.setGrade(gradeDTO.getGrade());

        Grade createdGrade = gradeService.addGrade(grade);
        return new ResponseEntity<>(createdGrade, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Grade> updateGrade(@PathVariable Long id, @RequestBody GradeDTO gradeDTO) {
        Grade existingGrade = gradeService.getGradeById(id);
        if (existingGrade == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Student student = studentRepository.findById(gradeDTO.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        Course course = courseRepository.findById(gradeDTO.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found"));

        existingGrade.setStudent(student);
        existingGrade.setCourse(course);
        existingGrade.setGrade(gradeDTO.getGrade());

        Grade updatedGrade = gradeService.updateGrade(id, existingGrade);

        return new ResponseEntity<>(updatedGrade, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGrade(@PathVariable Long id) {
        gradeService.deleteGrade(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Grade>> getGradesByStudent(@PathVariable Long studentId) {
        List<Grade> grades = gradeService.getGradesByStudent(studentId);
        return new ResponseEntity<>(grades, HttpStatus.OK);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Grade>> getGradesByCourse(@PathVariable Long courseId) {
        List<Grade> grades = gradeService.getGradesByCourse(courseId);
        return new ResponseEntity<>(grades, HttpStatus.OK);
    }
}
