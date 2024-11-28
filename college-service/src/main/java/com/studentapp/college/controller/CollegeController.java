package com.studentapp.college.controller;

import com.studentapp.college.model.Attendance;
import com.studentapp.college.model.StudentMarks;
import com.studentapp.college.repository.AttendanceRepository;
import com.studentapp.college.repository.StudentMarksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/college")
@RequiredArgsConstructor
public class CollegeController {

    private final StudentMarksRepository marksRepository;
    private final AttendanceRepository attendanceRepository;

    // Marks endpoints
    @PostMapping("/marks")
    public ResponseEntity<StudentMarks> addMarks(@RequestBody StudentMarks marks) {
        return ResponseEntity.ok(marksRepository.save(marks));
    }

    @GetMapping("/marks/student/{studentId}")
    public ResponseEntity<List<StudentMarks>> getStudentMarks(@PathVariable Long studentId) {
        return ResponseEntity.ok(marksRepository.findByStudentId(studentId));
    }

    @GetMapping("/marks/student/{studentId}/semester/{semester}")
    public ResponseEntity<List<StudentMarks>> getStudentMarksBySemester(
            @PathVariable Long studentId,
            @PathVariable String semester) {
        return ResponseEntity.ok(marksRepository.findByStudentIdAndSemester(studentId, semester));
    }

    // Attendance endpoints
    @PostMapping("/attendance")
    public ResponseEntity<Attendance> markAttendance(@RequestBody Attendance attendance) {
        return ResponseEntity.ok(attendanceRepository.save(attendance));
    }

    @GetMapping("/attendance/student/{studentId}")
    public ResponseEntity<List<Attendance>> getStudentAttendance(@PathVariable Long studentId) {
        return ResponseEntity.ok(attendanceRepository.findByStudentId(studentId));
    }

    @GetMapping("/attendance/student/{studentId}/range")
    public ResponseEntity<List<Attendance>> getStudentAttendanceByDateRange(
            @PathVariable Long studentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(attendanceRepository.findByStudentIdAndDateBetween(studentId, startDate, endDate));
    }
}
