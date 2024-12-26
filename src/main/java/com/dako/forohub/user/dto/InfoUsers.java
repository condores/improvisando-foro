package com.dako.forohub.user.dto;

import java.util.stream.Collectors;

import com.dako.forohub.user.domain.RolesEnum;
import com.dako.forohub.user.domain.User;

public record InfoUsers(
        String name,
        String username,
        String email,
        String roles) {
    public InfoUsers(User user) {
        this(user.getName(), user.getUsername(), user.getEmail(),
                user.getRoles().isEmpty() ? RolesEnum.USER.name()
                        : user.getRoles().stream()
                                .map(role -> role.getName().name())
                                .collect(Collectors.joining(", ")));
    }
}