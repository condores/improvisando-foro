package com.dako.forohub.topic.dtos;

import java.time.LocalDate;
import java.util.List;

import com.dako.forohub.comment.dtos.CommentDto;
import com.dako.forohub.topic.domain.TopicStatusEnum;

public record TopicDto(
        Long id,
        String title,
        String message,
        LocalDate createdAt,
        TopicStatusEnum status,
        String authorName,
        LocalDate updatedAt,
        List<CommentDto> comments
        ) {

}
