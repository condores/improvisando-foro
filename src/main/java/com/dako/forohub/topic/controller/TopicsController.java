package com.dako.forohub.topic.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dako.forohub.response.ApiResponse;
import com.dako.forohub.response.DataResponse;
import com.dako.forohub.topic.dto.NewTopicRequestDto;
import com.dako.forohub.topic.dto.TopicDto;
import com.dako.forohub.topic.service.TopicService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("topics")
public class TopicsController {

    private final TopicService topicService;

    public TopicsController(TopicService topicService) {
        this.topicService = topicService;
    }

    // Endpoint para crear un nuevo tema)
    @PostMapping("new")
    public ResponseEntity<ApiResponse> createNewTopic(@RequestBody @Valid NewTopicRequestDto newTopicRequestDto) {
        try {
            topicService.createTopic(newTopicRequestDto.title(), newTopicRequestDto.message(), LocalDateTime.now());
            return ResponseEntity.ok(new ApiResponse("Topic created successfully", 201));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    // Topicos por autor (GET /topics/{id}/topics)
    @GetMapping("{id}/topics")
    public ResponseEntity<DataResponse<List<TopicDto>>> getTopicsByAuthorId(@PathVariable Long id) {
        List<TopicDto> topics = topicService.allTopicsById(id);
        if (topics == null || topics.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new DataResponse<>("Topics retrieved successfully", 200, topics));
    }

    // Todos los temas (GET /topics)
    @GetMapping("all")
    public ResponseEntity<DataResponse<Page<TopicDto>>> getAllTopics(
            @PageableDefault(size = 10, page = 0, sort = "createdAt", direction = Direction.DESC) Pageable pageable) {
        Page<TopicDto> topics = topicService.getAllTopics(pageable);
        return ResponseEntity.ok(new DataResponse<>("Topics retrieved successfully", 200, topics));
    }

}
