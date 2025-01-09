package com.dako.forohub.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dako.forohub.infra.responses.ApiResponse;
import com.dako.forohub.user.dtos.UserRegisterRequestDto;
import com.dako.forohub.user.service.CreateUserService;

import jakarta.validation.Valid;
@RestController
@RequestMapping("users")
public class UserController {

    private final CreateUserService createUserService;

    public UserController(CreateUserService createUserService) {
        this.createUserService = createUserService;
    }

    @PostMapping("register")
    public ResponseEntity<ApiResponse> createUser(@Valid @RequestBody UserRegisterRequestDto userDto) {
        createUserService.createUser(userDto);
        ApiResponse response = new ApiResponse("User registered successfully", HttpStatus.CREATED.value());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}