package com.fitness.aiservice.service;

import com.fitness.aiservice.dto.Activity;
import com.fitness.aiservice.model.Recommendation;
import com.fitness.aiservice.repository.RecommendationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityMessageListener {

    private final ActivityAIService aiService;
    private final RecommendationRepository recommendationRepository;

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void processActivity(Activity activity){
        log.info("Received activity for recommendation with id: {}", activity.getId());
        // log.info("Generated Recommendation: {}", aiService.generateRecommendation(activity));

        Recommendation recommendation = aiService.generateRecommendation(activity);

        // save the recommendation to the database
        recommendationRepository.save(recommendation);

    }
}
