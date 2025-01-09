package com.dako.forohub.user.dtos;

import java.util.stream.Collectors;

import com.dako.forohub.user.domain.RolesEnum;
import com.dako.forohub.user.domain.User;

public record UserInfoDto(
                String name,
                String username,
                String email,
                String roles) {
        public UserInfoDto(User user) {
                this(user.getName(), user.getUsername(), user.getEmail(),
                                user.getRoles().isEmpty() ? RolesEnum.USER.name()
                                                : user.getRoles().stream()
                                                                .map(role -> role.getName().name())
                                                                .collect(Collectors.joining(", ")));
        }
}