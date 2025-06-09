package com.fitness.activityservice.service;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.model.Activity;
import com.fitness.activityservice.repository.ActivityRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserValidationService userValidationService;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    /**
     * The activity service is a microservice and it is not monolith. We are doing the activity tracking
     * for a particular user. To validate the user we need to make a call to the user service. This
     * service will tell us whether a user exists or not. If the user does not exist then we will throw
     * a runtime exception. If the user exists then we will save the activity in the database.
     *
     * We are using webclient to make a call to the user service. The webclient is configured to use
     * the ribbon load balancer to make the call to the user service.
     *
     * The activity service is using the circuit breaker pattern to make the call to the user service.
     * If the user service is down then the circuit breaker will detect it and will not make the call
     * to the user service. If the user service is up then the circuit breaker will make the call.
     */
    public ActivityResponse trackActivity(ActivityRequest request) {
        log.info("Tracking activity: {}", request);
        boolean isValidUser = userValidationService.validateUserProfile(request.getUserId());

        if(!isValidUser) {
            log.error("User not found with id: {}", request.getUserId());
            throw new RuntimeException("User not found with id " + request.getUserId());
        }
        Activity activity = Activity.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMetrics(request.getAdditionalMetrics())
                .build();

        Activity savedActivity = activityRepository.save(activity);
        log.info("Activity saved with id: {}", savedActivity.getId());

        /**
         * Publish the activity to the RabbitMQ exchange to be processed by AI
         * The activity is published with the routing key 'activity.tracking
         * The AI service will receive the activity and process it
         */
        try{
            rabbitTemplate.convertAndSend(exchange, routingKey, savedActivity);
            log.debug("Activity with id {} published to RabbitMQ exchange: {}", savedActivity.getId(), exchange);
        } catch (Exception e) {
            log.error("Failed to publish activity with id {} to RabbitMQ exchange: {}", savedActivity.getId(), exchange, e.getMessage());
        }

        return mapToActivityResponse(savedActivity) ;
    }

    /**
     * Converts an Activity entity to an ActivityResponse DTO.
     * This method is used to map the fields of the Activity entity
     * to the corresponding fields in the ActivityResponse object.
     *
     * @param activity the Activity entity to be mapped
     * @return the mapped ActivityResponse object
     */
    private ActivityResponse mapToActivityResponse(Activity activity) {
        log.debug("Mapping Activity entity to ActivityResponse: {}", activity);

        // Create a new ActivityResponse object and populate its fields
        ActivityResponse response = new ActivityResponse();

        response.setId(activity.getId());
        response.setUserId(activity.getUserId());
        response.setType(activity.getType());
        response.setDuration(activity.getDuration());
        response.setCaloriesBurned(activity.getCaloriesBurned());
        response.setStartTime(activity.getStartTime());
        response.setAdditionalMetrics(activity.getAdditionalMetrics());
        response.setCreatedAt(activity.getCreatedAt());
        response.setFinishedAt(activity.getFinishedAt());

        log.debug("Mapped ActivityResponse: {}", response);
        return response;
    }

    /**
     * Fetches all activities for a given user and returns them as a list of ActivityResponse objects.
     */
    public List<ActivityResponse> getUserActivities(String userId) {
        log.info("Fetching Activities for userId: {}", userId);

        // Use the findByUserId method of ActivityRepository to fetch the list of Activities
        List<Activity> activities = activityRepository.findByUserId(userId);

        // Map the list of Activities to ActivityResponse objects using the mapToActivityResponse method
        // and collect the results in a list
        return activities.stream()
                .map(this::mapToActivityResponse)
                .collect(Collectors.toList());
    }

    /**
     * Finds an Activity by its id and returns it as an ActivityResponse.
     * If the Activity is not found, it throws a RuntimeException.
     */
    public ActivityResponse getActivityById(String activityId) {
        log.info("Fetching Activity with id: {}", activityId);
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> {
                    log.error("Activity not found with id: {}", activityId);
                    return new RuntimeException("Activity not found with id " + activityId);
                });

        return mapToActivityResponse(activity);
    }
}
