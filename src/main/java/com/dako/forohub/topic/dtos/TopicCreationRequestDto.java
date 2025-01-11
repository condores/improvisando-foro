package com.dako.forohub.topic.dtos;

import java.time.LocalDateTime;

import com.dako.forohub.Course.domain.CourseCategoryEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TopicCreationRequestDto(
    @NotBlank(message = "El campo título es obligatorio") 
    String title,

    @NotBlank(message = "Debes incluir un mensaje en tu post") 
    String message,

    @NotNull(message = "La categoría del curso es obligatoria")
    CourseCategoryEnum category,

    @NotBlank(message = "El nombre del curso es obligatorio")
    String courseName,

    LocalDateTime createdAt
) {
    public LocalDateTime getCreatedAt() {
        return createdAt == null ? LocalDateTime.now() : createdAt;
    }
}
