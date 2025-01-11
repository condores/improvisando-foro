package com.dako.forohub.topic.controllers;


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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("topics")
@Tag(name = "Topic Creation", description = "Endpoints for creating new topics")
public class TopicsCreationController {

    private final TopicCreationService topicCreationService;

    public TopicsCreationController(TopicCreationService topicCreationService) {
        this.topicCreationService = topicCreationService;
    }

    @PostMapping("new")
    @Operation(summary = "Create a New Topic", description = "Endpoint to create a new topic in the forum", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<ApiResponse> createNewTopic(@RequestBody @Valid TopicCreationRequestDto newTopicRequestDto) {
        try {
            topicCreationService.createNewTopic(
                newTopicRequestDto.title(), 
                newTopicRequestDto.message(),
                newTopicRequestDto.category(),
                newTopicRequestDto.courseName(),  // Añade este argumento
                newTopicRequestDto.getCreatedAt()
            );
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