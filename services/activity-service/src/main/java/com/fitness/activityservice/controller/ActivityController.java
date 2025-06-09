package com.fitness.activityservice.controller;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.service.ActivityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/activities")
public class ActivityController {
    private final ActivityService activityService;

    @PostMapping
    public ResponseEntity<ActivityResponse> trackActivity(@RequestBody ActivityRequest request, @RequestHeader("X-User-ID") String userId){
        log.info("Received Request to track activity for user: {}", userId);
        if(userId != null){
            request.setUserId(userId);
        }
        return new ResponseEntity<>(activityService.trackActivity(request), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getUserActivities(@RequestHeader("X-User-ID") String userId){
        log.info("Received Request to get activities for user: {}", userId);
        return new ResponseEntity<>(activityService.getUserActivities(userId), HttpStatus.OK);
    }

    @GetMapping("/{activityId}")
    public ResponseEntity<ActivityResponse> getActivityById(@PathVariable String activityId){
        log.info("Received Request to get activity for a particular activity: {}", activityId);
        return new ResponseEntity<>(activityService.getActivityById(activityId), HttpStatus.OK);
    }



}

