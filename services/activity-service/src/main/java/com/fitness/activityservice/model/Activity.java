package com.fitness.activityservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "activities")
public class Activity {

    @Id
    private String id;

    private String userId;
    private ActivityType type;
    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime startTime;

    @Field("metrics")
    Map<String, Object> additionalMetrics;

    /**
     * When using MongoDB, to correctly capture and store LocalDateTime fields,
     * you need to create a configuration file, {@code MongoConfig}, and enable
     * MongoDB auditing with {@code @EnableMongoAuditing}. Without enabling auditing,
     * the fields will not be automatically populated and will return {@code null}.
     */
    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime finishedAt;

}
