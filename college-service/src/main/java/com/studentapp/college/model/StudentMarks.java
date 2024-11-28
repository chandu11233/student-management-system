package com.studentapp.college.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentMarks {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long studentId;
    private String subject;
    private Integer marks;
    private String semester;
    private String examType; // MIDTERM, FINAL, etc.
    
    @Column(name = "academic_year")
    private String academicYear;
}
