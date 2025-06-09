package com.fitness.userservice.service;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.model.User;
import com.fitness.userservice.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponse getUserProfile(String userId) {
        log.info("Getting user profile for user id: {}", userId);

        return userRepository.findById(userId)
                .map(user -> {
                    UserResponse response = new UserResponse();
                    response.setId(user.getId());
                    response.setKeycloakId(user.getKeycloakId());
                    response.setEmail(user.getEmail());
                    response.setPassword(user.getPassword());
                    response.setFirstName(user.getFirstName());
                    response.setLastName(user.getLastName());
                    response.setCreatedAt(user.getCreatedAt());
                    response.setUpdatedAt(user.getUpdatedAt());
                    log.info("User profile: {}", response);
                    return response;
                })
                .orElseThrow(() -> {
                    log.error("User not found with id: {}", userId);
                    return new RuntimeException("User not found");
                });
    }

    public UserResponse registerUser(@Valid RegisterRequest request) {
        // check if a user already exists
        if(userRepository.existsByEmail(request.getEmail())){
            log.error("User already exists with email: {}", request.getEmail());
            User existingUser = userRepository.findByEmail(request.getEmail());
            UserResponse response = new UserResponse();

            response.setId(existingUser.getId());
            response.setKeycloakId(existingUser.getKeycloakId());
            response.setEmail(existingUser.getEmail());
            response.setPassword(existingUser.getPassword());
            response.setFirstName(existingUser.getFirstName());
            response.setLastName(existingUser.getLastName());
            response.setCreatedAt(existingUser.getCreatedAt());
            response.setUpdatedAt(existingUser.getUpdatedAt());

            return response;
        }

        User user = new User();
        log.info("Registering user: {}", request);
        user.setEmail(request.getEmail());
        user.setKeycloakId(request.getKeycloakId());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        User savedUser = userRepository.save(user);
        UserResponse response = new UserResponse();

        response.setId(savedUser.getId());
        response.setKeycloakId(savedUser.getKeycloakId());
        response.setEmail(savedUser.getEmail());
        response.setPassword(savedUser.getPassword());
        response.setFirstName(savedUser.getFirstName());
        response.setLastName(savedUser.getLastName());
        response.setCreatedAt(savedUser.getCreatedAt());
        response.setUpdatedAt(savedUser.getUpdatedAt());
        log.info("User created: {}", response);

        return response;
    }

    public Boolean validateUserProfile(String userId) {
        log.info("Validating user profile for user id: {}", userId);
        return userRepository.existsByKeycloakId(userId);
    }
}
