package com.studentapp.college.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "Student ID cannot be null")
    private Long studentId;

    @NotNull(message = "Date cannot be null")
    private LocalDate date;

    @NotNull(message = "Status cannot be null")
    @Column(name = "status")
    private String status; // "PRESENT" or "ABSENT"

    private String subject;
    private boolean present;
    
    @Column(name = "academic_year")
    private String academicYear;
}
