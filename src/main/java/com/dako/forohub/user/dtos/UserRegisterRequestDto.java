package com.dako.forohub.user.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRegisterRequestDto(
                @NotBlank @Size(min = 2, max = 10) String name,
                @NotBlank @Size(min = 3, max = 10) String username,
                @NotBlank @Email String email,
                @NotNull @Size(min = 8) String password) {

}
