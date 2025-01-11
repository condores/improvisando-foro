package com.dako.forohub.Comments.dtos;

import jakarta.validation.constraints.NotBlank;

public record CommentRequestCreationDto(
        @NotBlank(message = "El mensaje no puede estar vacío") String message) {

}
