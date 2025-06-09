package com.fitness.aiservice.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class RecommendationResponse {
    private String recommendationId;
    private String userId;
    private String activityId;
    private String activityType;
    private String recommendation;
    private List<String> improvements;
    private List<String> suggestions;
    private List<String> safety;
    private LocalDateTime createdAt;
}
