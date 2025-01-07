package com.dako.forohub.topic.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;

public record NewTopicRequestDto(
                @NotBlank(message = "el campo titulo es obligatorio") String title,
                @NotBlank(message = "Debes incluir un mensaje en tu post") String message,
                LocalDateTime createdAt) {
        public LocalDateTime getCreatedAt() {
                return createdAt == null ? LocalDateTime.now() : createdAt;
        }
}
