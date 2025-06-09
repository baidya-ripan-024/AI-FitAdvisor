package com.fitness.aiservice.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class Activity {
    private String id;
    private String userId;
    private String type;
    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime startTime;
    Map<String, Object> additionalMetrics;
    private LocalDateTime createdAt;
    private LocalDateTime finishedAt; // we can name it as updatedAt
}
