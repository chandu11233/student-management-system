package com.studentapp.college.controller;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.studentapp.college.exception.ResourceNotFoundException;
import com.studentapp.college.model.Attendance;
import com.studentapp.college.model.StudentMarks;
import com.studentapp.college.repository.AttendanceRepository;
import com.studentapp.college.repository.StudentMarksRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/college")
@RequiredArgsConstructor
public class CollegeController {

    private final StudentMarksRepository marksRepository;
    private final AttendanceRepository attendanceRepository;

    // Marks endpoints
    @PostMapping("/marks")
    public ResponseEntity<StudentMarks> addMarks(@Valid @RequestBody StudentMarks marks) {
        validateMarks(marks);
        return ResponseEntity.ok(marksRepository.save(marks));
    }

    @PostMapping("/marks/bulk")
    public ResponseEntity<List<StudentMarks>> addBulkMarks(@Valid @RequestBody List<StudentMarks> marksList) {
        marksList.forEach(this::validateMarks);
        return ResponseEntity.ok(marksRepository.saveAll(marksList));
    }

    @GetMapping("/marks/student/{studentId}")
    public ResponseEntity<List<StudentMarks>> getStudentMarks(@PathVariable Long studentId) {
        List<StudentMarks> marks = marksRepository.findByStudentId(studentId);
        if (marks.isEmpty()) {
            throw new ResourceNotFoundException("No marks found for student: " + studentId);
        }
        return ResponseEntity.ok(marks);
    }

    @GetMapping("/marks/student/{studentId}/semester/{semester}")
    public ResponseEntity<List<StudentMarks>> getStudentMarksBySemester(
            @PathVariable Long studentId,
            @PathVariable String semester) {
        List<StudentMarks> marks = marksRepository.findByStudentIdAndSemester(studentId, semester);
        if (marks.isEmpty()) {
            throw new ResourceNotFoundException("No marks were found for student: " + studentId + " in semester: " + semester);
        }
        return ResponseEntity.ok(marks);
    }

    // Attendance endpoints
    @PostMapping("/attendance")
    public ResponseEntity<Attendance> markAttendance(@Valid @RequestBody Attendance attendance) {
        validateAttendance(attendance);
        return ResponseEntity.ok(attendanceRepository.save(attendance));
    }

    @PostMapping("/attendance/bulk")
    public ResponseEntity<List<Attendance>> markBulkAttendance(@Valid @RequestBody List<Attendance> attendanceList) {
        attendanceList.forEach(this::validateAttendance);
        return ResponseEntity.ok(attendanceRepository.saveAll(attendanceList));
    }

    @GetMapping("/attendance/student/{studentId}")
    public ResponseEntity<List<Attendance>> getStudentAttendance(@PathVariable Long studentId) {
        List<Attendance> attendance = attendanceRepository.findByStudentId(studentId);
        if (attendance.isEmpty()) {
            throw new ResourceNotFoundException("No attendance records found for student: " + studentId);
        }
        return ResponseEntity.ok(attendance);
    }

    @GetMapping("/attendance/student/{studentId}/range")
    public ResponseEntity<List<Attendance>> getStudentAttendanceByDateRange(
            @PathVariable Long studentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        List<Attendance> attendance = attendanceRepository.findByStudentIdAndDateBetween(studentId, startDate, endDate);
        if (attendance.isEmpty()) {
            throw new ResourceNotFoundException("No attendance records found for student: " + studentId + " between dates");
        }
        return ResponseEntity.ok(attendance);
    }

    @GetMapping("/attendance/student/{studentId}/percentage")
    public ResponseEntity<Map<String, Double>> getAttendancePercentage(
            @PathVariable Long studentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date must be before end date");
        }
        
        List<Attendance> attendance = attendanceRepository.findByStudentIdAndDateBetween(studentId, startDate, endDate);
        if (attendance.isEmpty()) {
            throw new ResourceNotFoundException("No attendance records found for student: " + studentId + " between dates");
        }
        
        long totalDays = attendance.size();
        long presentDays = attendance.stream()
                .filter(a -> "PRESENT".equalsIgnoreCase(a.getStatus()))
                .count();
        
        double percentage = (presentDays * 100.0) / totalDays;
        
        Map<String, Double> response = new HashMap<>();
        response.put("attendancePercentage", Math.round(percentage * 100.0) / 100.0);
        
        return ResponseEntity.ok(response);
    }

    private void validateMarks(StudentMarks marks) {
        if (marks.getMarks() < 0 || marks.getMarks() > 100) {
            throw new IllegalArgumentException("Marks must be between 0 and 100");
        }
    }

    private void validateAttendance(Attendance attendance) {
        if (attendance.getDate() == null) {
            throw new IllegalArgumentException("Attendance date cannot be null");
        }
        if (attendance.getDate().isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Attendance date cannot be in the future");
        }
        if (!Arrays.asList("PRESENT", "ABSENT").contains(attendance.getStatus().toUpperCase())) {
            throw new IllegalArgumentException("Attendance status must be either PRESENT or ABSENT");
        }
    }
}
