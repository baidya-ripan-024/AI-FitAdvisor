package com.fitness.aiservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

/**
 * This service is responsible for sending requests to the Gemini API to get answers to prompts.
 */
@Service
@Slf4j
public class GeminiService {

    private final WebClient webClient;

    @Value("${gemini.api.url}")
    private String geminiApiUrl;

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public GeminiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.build();
    }

    /**
     * Sends a request to the Gemini API to get an answer to the given prompt.
     * The request body is in the following format:
     * {
     *   "contents": [{
     *     "parts":[{"text": "Explain how AI works"}]
     *     }]
     *    }
     */
    public String getAnswer(String prompt){
        Map<String, Object> requestBody =
                Map.of("contents", new Object[]{
                        Map.of("parts", new Object[]{
                                /**
                                 * Whatever the prompt is, it will be in the text field.
                                 * make sure follow the GEMINI_API_REQUEST_BODY_FORMAT
                                */
                                Map.of("text", prompt)
                        })
                }
        );

        log.info("Sending request to Gemini API with prompt: {}", prompt);

        String response = webClient.post()
                .uri(geminiApiUrl + geminiApiKey)
                .header("Content-Type", "application/json")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        log.info("Received response from Gemini API: {}", response);

        return response;
    }
}