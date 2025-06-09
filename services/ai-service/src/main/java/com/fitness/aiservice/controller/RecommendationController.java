package com.fitness.aiservice.controller;

import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    /**
     * get all recommendations based on a particular userId
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Recommendation>> getUserRecommendation(@PathVariable String userId){
        log.info(" Received Request to Getting recommendation for user id: {}", userId);
        return new ResponseEntity<>(recommendationService.getUserRecommendation(userId), HttpStatus.OK);
    }

    /**
     * get recommendation based on a particular activityId
     */
    @GetMapping("/activity/{activityId}")
    public ResponseEntity<Recommendation> getActivityRecommendation(@PathVariable String activityId){
        log.info("Received Request to Getting recommendation for activity id: {}", activityId);
        return new ResponseEntity<>(recommendationService.getActivityRecommendation(activityId), HttpStatus.OK);
    }

}
