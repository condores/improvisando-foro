package com.dako.forohub.topic.controllers;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dako.forohub.infra.exceptions.ResourceNotFoundException;
import com.dako.forohub.infra.responses.ApiResponse;
import com.dako.forohub.topic.dtos.TopicCreationRequestDto;
import com.dako.forohub.topic.services.TopicCreationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("topics")
public class TopicsCreationController {

    private final TopicCreationService topicCreationService;

    public TopicsCreationController(TopicCreationService topicCreationService) {
        this.topicCreationService = topicCreationService;
    }

    @PostMapping("new")
    public ResponseEntity<ApiResponse> createNewTopic(@RequestBody @Valid TopicCreationRequestDto newTopicRequestDto) {
        try {
            topicCreationService.createNewTopic(newTopicRequestDto.title(), newTopicRequestDto.message(),
                    LocalDateTime.now());
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse("Topic created successfully", HttpStatus.CREATED.value()));
        } catch (ResourceNotFoundException e) {
            return buildErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return buildErrorResponse("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<ApiResponse> buildErrorResponse(String message, HttpStatus status) {
        return ResponseEntity.status(status).body(new ApiResponse(message, status.value()));
    }
}