package com.dako.forohub.comment.dtos;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;

public record CommentDto(
                Long id,
                @NotBlank(message = "The message cannot be empty") String message,
                String authorName,
                Long topicId,
                LocalDateTime createdAt) {

}
