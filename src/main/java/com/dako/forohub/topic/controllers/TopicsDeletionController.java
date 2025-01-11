package com.dako.forohub.topic.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dako.forohub.infra.exceptions.ResourceNotFoundException;
import com.dako.forohub.infra.responses.ApiResponse;
import com.dako.forohub.topic.services.TopicDeletionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
@RestController
@RequestMapping("topics")
@Tag(name = "Topic Deletion", description = "Endpoints for deleting topics")
public class TopicsDeletionController {

    private final TopicDeletionService topicDeletionService;

    public TopicsDeletionController(TopicDeletionService topicDeletionService) {
        this.topicDeletionService = topicDeletionService;
    }

    @DeleteMapping("{idTopic}")
    @Operation(summary = "Delete a Topic", description = "Endpoint to delete a topic by its ID",security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<ApiResponse> deleteTopic(@PathVariable Long idTopic) {
        try {
            topicDeletionService.deleteTopic(idTopic);
            return ResponseEntity.ok(new ApiResponse("Topic deleted successfully", HttpStatus.OK.value()));
        } catch (ResourceNotFoundException e) {
            return buildErrorResponse(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return buildErrorResponse(e.getMessage(), HttpStatus.FORBIDDEN);
        }
    }

    private ResponseEntity<ApiResponse> buildErrorResponse(String message, HttpStatus status) {
        return ResponseEntity.status(status).body(new ApiResponse(message, status.value()));
    }
}