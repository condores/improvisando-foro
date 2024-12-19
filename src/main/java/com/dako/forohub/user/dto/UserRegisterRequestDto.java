package com.dako.forohub.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRegisterRequestDto(
        @NotBlank String name,
        @NotBlank String username,
        @NotBlank String email,
        @NotNull char[] password) {

}
