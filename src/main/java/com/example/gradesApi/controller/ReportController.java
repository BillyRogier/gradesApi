package com.example.gradesApi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.gradesApi.service.ReportService;

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/course/{courseId}")
    public String generateCourseReport(@PathVariable Long courseId) {
        return reportService.generateCourseReport(courseId);
    }

    @GetMapping("/student/{studentId}")
    public String generateStudentReport(@PathVariable Long studentId) {
        return reportService.generateStudentReport(studentId);
    }
}
