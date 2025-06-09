package com.fitness.gateway.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final WebClient userServiceWebClient;

    /**
     * Validates whether a user profile exists or not.
     */
    public Mono<Boolean> validateUserProfile(String userId) {

        log.info("Validating user profile for user id: {}", userId);

        return userServiceWebClient
                .get()
                .uri("http://user-service/api/users/profile/{userId}/validate", userId)
                .retrieve()
                .bodyToMono(Boolean.class)
                .onErrorResume(WebClientResponseException.class, e -> {

                    if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                        return Mono.error(new RuntimeException("User not found with id " + userId));
                    } else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                        return Mono.error(new RuntimeException("Invalid user id " + userId));
                    }

                    return Mono.error(new RuntimeException("Error validating user profile " + e.getMessage()));
                });
    }

    /**
     * Registers a user with the provided register request.
     */
    public Mono<UserResponse> registerUser(RegisterRequest request) {
        log.info("Calling User Registration API for email: {}", request.getEmail());
        return userServiceWebClient
                .post()
                .uri("http://user-service/api/users/register")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .onErrorResume(WebClientResponseException.class, e -> {

                    if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                        return Mono.error(new RuntimeException("Bad Request: " + e.getMessage()));
                    } else if (e.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR) {
                        return Mono.error(new RuntimeException("Internal Server Error: " + e.getMessage()));
                    }
                    return Mono.error(new RuntimeException("Unexpected error: " + e.getMessage()));
                });
    }
}