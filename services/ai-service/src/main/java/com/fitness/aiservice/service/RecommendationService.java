package com.fitness.aiservice.service;

import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final RecommendationRepository recommendationRepository;

    public List<Recommendation> getUserRecommendation(String userId) {
        log.info("Getting recommendation for user id: {}", userId);
        return recommendationRepository.findByUserId(userId);
    }

    public Recommendation getActivityRecommendation(String activityId) {
        log.info("Getting recommendation for activity id: {}", activityId);
        return recommendationRepository.findByActivityId(activityId)
                .orElseThrow(() -> {
                    log.error("No recommendation found for activity id: {}", activityId);
                    return new RuntimeException("No recommendation found for activity id: " + activityId);
                });
    }
}
