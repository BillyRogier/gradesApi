package com.example.gradesApi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.gradesApi.model.Grade;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findByStudentId(Long studentId);

    List<Grade> findByCourseId(Long courseId);

    List<Grade> findByStudentIdAndCourseId(Long studentId, Long courseId);

    @Query("SELECT AVG(g.grade) FROM Grade g WHERE g.course.id = :courseId")
    double findAverageGradeByCourse(Long courseId);

    @Query("SELECT AVG(g.grade) FROM Grade g WHERE g.student.id = :studentId")
    double findAverageGradeByStudent(Long studentId);
}
