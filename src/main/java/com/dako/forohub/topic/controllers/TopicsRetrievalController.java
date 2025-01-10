package com.dako.forohub.topic.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dako.forohub.infra.exceptions.ResourceNotFoundException;
import com.dako.forohub.infra.responses.DataResponse;
import com.dako.forohub.topic.dtos.TopicDto;
import com.dako.forohub.topic.services.TopicRetrievalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
@RestController
@RequestMapping("topics")
@Tag(name = "Topic Retrieval", description = "Endpoints for retrieving topics")
public class TopicsRetrievalController {

    private final TopicRetrievalService topicRetrievalService;

    public TopicsRetrievalController(TopicRetrievalService topicRetrievalService) {
        this.topicRetrievalService = topicRetrievalService;
    }

    @GetMapping("/authors/{id}/topics")
    @Operation(summary = "Get Topics by Author ID", description = "Retrieve topics created by a specific author")
    public ResponseEntity<DataResponse<Page<TopicDto>>> getTopicsByAuthorId(@PathVariable Long id,
            @PageableDefault(size = 5, page = 0, sort = "createdAt", direction = Direction.DESC) Pageable pageable) {
        Page<TopicDto> topics = topicRetrievalService.allTopicsById(id, pageable);
        if (topics == null || topics.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(new DataResponse<>("Topics retrieved successfully", HttpStatus.OK.value(), topics));
    }

    @GetMapping("/mine")
    
    @Operation(summary = "Get My Topics", description = "Retrieve topics created by the authenticated user",security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<DataResponse<Page<TopicDto>>> getMyTopics(
            @PageableDefault(size = 5, page = 0, sort = "createdAt", direction = Direction.DESC) Pageable pageable) {
        try {
            Page<TopicDto> topics = topicRetrievalService.getMyTopics(pageable);
            if (topics == null || topics.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity
                    .ok(new DataResponse<>("Topics retrieved successfully", HttpStatus.OK.value(), topics));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new DataResponse<>(e.getMessage(), HttpStatus.NOT_FOUND.value(), Page.empty()));
        }
    }

    @GetMapping
    @Operation(summary = "Get All Topics", description = "Retrieve all topics in the forum")
    public ResponseEntity<DataResponse<Page<TopicDto>>> getAllTopics(
            @PageableDefault(size = 10, page = 0, sort = "createdAt", direction = Direction.DESC) Pageable pageable) {
        Page<TopicDto> topics = topicRetrievalService.getAllTopics(pageable);
        return ResponseEntity.ok(new DataResponse<>("Topics retrieved successfully", HttpStatus.OK.value(), topics));
    }
}
