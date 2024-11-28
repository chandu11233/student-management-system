package com.studentapp.email.controller;

import com.studentapp.email.dto.EmailRequest;
import com.studentapp.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/send-marks")
    public void sendMarksEmail(@RequestBody EmailRequest request) {
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("studentName", request.getStudentName());
        templateModel.put("marks", request.getMarks());
        templateModel.put("semester", request.getSemester());
        
        // Send to student
        emailService.sendEmail(
            request.getStudentEmail(),
            "Your Academic Performance Update",
            "marks-notification",
            templateModel
        );
        
        // Send to parent
        emailService.sendEmail(
            request.getParentEmail(),
            "Your Child's Academic Performance Update",
            "marks-notification-parent",
            templateModel
        );
    }

    @PostMapping("/send-attendance")
    public void sendAttendanceEmail(@RequestBody EmailRequest request) {
        Map<String, Object> templateModel = new HashMap<>();
        templateModel.put("studentName", request.getStudentName());
        templateModel.put("attendance", request.getAttendance());
        templateModel.put("period", request.getPeriod());
        
        // Send to student
        emailService.sendEmail(
            request.getStudentEmail(),
            "Your Attendance Update",
            "attendance-notification",
            templateModel
        );
        
        // Send to parent
        emailService.sendEmail(
            request.getParentEmail(),
            "Your Child's Attendance Update",
            "attendance-notification-parent",
            templateModel
        );
    }
}
