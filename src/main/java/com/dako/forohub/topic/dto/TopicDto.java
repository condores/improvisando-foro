package com.dako.forohub.topic.dto;

import java.time.LocalDate;

import com.dako.forohub.topic.domain.TopicStatusEnum;

public record TopicDto(
    Long id,
    String title,
    String message,
    LocalDate createdAt,
    TopicStatusEnum status,
    String authorName
) {
    
}
