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

@RestController
@RequestMapping("topics")
public class TopicsRetrievalController {

    private final TopicRetrievalService topicRetrievalService;

    public TopicsRetrievalController(TopicRetrievalService topicRetrievalService) {
        this.topicRetrievalService = topicRetrievalService;
    }

    @GetMapping("{id}/topics")
    public ResponseEntity<DataResponse<Page<TopicDto>>> getTopicsByAuthorId(@PathVariable Long id,
            @PageableDefault(size = 5, page = 0, sort = "createdAt", direction = Direction.DESC) Pageable pageable) {
        Page<TopicDto> topics = topicRetrievalService.allTopicsById(id, pageable);
        if (topics == null || topics.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(new DataResponse<>("Topics retrieved successfully", HttpStatus.OK.value(), topics));
    }

    @GetMapping("mytopics")
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

    @GetMapping("all")
    public ResponseEntity<DataResponse<Page<TopicDto>>> getAllTopics(
            @PageableDefault(size = 10, page = 0, sort = "createdAt", direction = Direction.DESC) Pageable pageable) {
        Page<TopicDto> topics = topicRetrievalService.getAllTopics(pageable);
        return ResponseEntity.ok(new DataResponse<>("Topics retrieved successfully", HttpStatus.OK.value(), topics));
    }
}