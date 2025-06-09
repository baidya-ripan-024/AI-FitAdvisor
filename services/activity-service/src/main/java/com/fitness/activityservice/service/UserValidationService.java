package com.fitness.activityservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

/**
 * This service is responsible for validating whether a user profile exists or not.
 * It uses the UserService to validate the user profile.
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class UserValidationService {

    private final WebClient.Builder userServiceWebClientBuilder;

    /**
     * Validates whether a user profile exists or not.
     * 
     * @param userId the user id to be validated
     * @return true if the user profile exists, false otherwise
     */
    public boolean validateUserProfile(String userId) {
        try {
            log.info("Validating user profile for user id: {}", userId);
            return userServiceWebClientBuilder.build()
                    .get()
                    .uri("http://user-service/api/users/profile/{userId}/validate", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
        } catch (WebClientException e) {
            log.error("Error validating user profile: {}", e.getMessage());
            if (e instanceof WebClientResponseException) {
                WebClientResponseException webClientResponseException = (WebClientResponseException) e;
                if (webClientResponseException.getStatusCode() == HttpStatus.NOT_FOUND) {
                    log.error("User not found with id: {}", userId);
                    throw new RuntimeException("User not found");
                } else if (webClientResponseException.getStatusCode() == HttpStatus.BAD_REQUEST) {
                    log.error("Invalid user id: {}", userId);
                    throw new RuntimeException("Invalid user id");
                }
            }
            return false; // Default return statement
        }
    }

}