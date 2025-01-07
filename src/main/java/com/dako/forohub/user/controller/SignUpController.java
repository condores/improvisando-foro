package com.dako.forohub.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dako.forohub.exceptions.DuplicateResourceException;
import com.dako.forohub.response.DataResponse;
import com.dako.forohub.user.dto.UserRegisterRequestDto;
import com.dako.forohub.user.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("signup")
public class SignUpController {

    private final UserService userService;

    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<DataResponse<Void>> createUser(@RequestBody @Valid UserRegisterRequestDto userDto) {
        try {
            userService.createUser(userDto);
            return ResponseEntity.ok(new DataResponse<>("User created successfully", 200));
        } catch (DuplicateResourceException e) {
            return ResponseEntity.badRequest().body(new DataResponse<>(e.getMessage(), 400));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new DataResponse<>(e.getMessage(), 400));
        }
    }

}