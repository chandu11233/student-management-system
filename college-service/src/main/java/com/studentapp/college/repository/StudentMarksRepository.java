package com.studentapp.college.repository;

import com.studentapp.college.model.StudentMarks;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface StudentMarksRepository extends JpaRepository<StudentMarks, Long> {
    List<StudentMarks> findByStudentId(Long studentId);
    List<StudentMarks> findByStudentIdAndSemester(Long studentId, String semester);
}
