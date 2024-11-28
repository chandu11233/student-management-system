package com.studentapp.email.dto;

import lombok.Data;
import java.util.Map;

@Data
public class EmailRequest {
    private String studentName;
    private String studentEmail;
    private String parentEmail;
    private String semester;
    private Map<String, Integer> marks;
    private Map<String, Double> attendance;
    private String period;
}
