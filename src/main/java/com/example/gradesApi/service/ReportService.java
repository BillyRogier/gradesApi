package com.example.gradesApi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.gradesApi.repository.GradeRepository;

@Service
public class ReportService {

    private final GradeRepository gradeRepository;

    @Autowired
    public ReportService(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    public String generateCourseReport(Long courseId) {
        double average = gradeRepository.findAverageGradeByCourse(courseId);
        return "Moyenne des notes pour le cours " + courseId + " : " + average;
    }

    public String generateStudentReport(Long studentId) {
        double average = gradeRepository.findAverageGradeByStudent(studentId);
        return "Moyenne des notes pour l'étudiant " + studentId + " : " + average;
    }
}
