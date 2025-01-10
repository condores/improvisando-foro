package com.dako.forohub.topic.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dako.forohub.infra.exceptions.ResourceNotFoundException;
import com.dako.forohub.infra.responses.ApiResponse;
import com.dako.forohub.topic.dtos.TopicUpdateDto;
import com.dako.forohub.topic.services.TopicUpdateService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("topics")
@Tag(name = "Topic Update", description = "Endpoints for updating topics")
public class TopicUpdateController {

    private final TopicUpdateService topicUpdateService;

    public TopicUpdateController(TopicUpdateService topicUpdateService) {
        this.topicUpdateService = topicUpdateService;
    }

    // Metodo para modificar un tema
    @PatchMapping("{idTopic}")
    @Operation(summary = "Update a Topic", description = "Endpoint to update an existing topic",security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<ApiResponse> updateTopic(@RequestBody @Valid TopicUpdateDto updateTopicDto,
            @PathVariable Long idTopic) {
        try {
            topicUpdateService.updateTopic(idTopic, updateTopicDto.title(), updateTopicDto.message(),
                    updateTopicDto.status(), updateTopicDto.updateAt());
            return ResponseEntity.ok(new ApiResponse("Topic updated successfully", HttpStatus.OK.value()));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(), HttpStatus.NOT_FOUND.value()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse(e.getMessage(), HttpStatus.FORBIDDEN.value()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(
                    "An unexpected error occurred updating the topic", HttpStatus.INTERNAL_SERVER_ERROR.value()));
        }
    }
}