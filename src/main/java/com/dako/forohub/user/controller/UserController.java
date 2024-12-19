package com.dako.forohub.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dako.forohub.response.DataResponse;
import com.dako.forohub.user.domain.User;
import com.dako.forohub.user.dto.UserRegisterRequestDto;
import com.dako.forohub.user.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("user")
public class UserController {

    // Constructor for UserController
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<DataResponse<Void>> createUser(@RequestBody @Valid UserRegisterRequestDto userDto) {
        if (userRepository.existsByEmail(userDto.email())) {
            return ResponseEntity.badRequest().body(new DataResponse<>("Email already exists", 400));
        }
        if (userRepository.existsByUsername(userDto.username())) {
            return ResponseEntity.badRequest().body(new DataResponse<>("Username already exists", 400));
        }

        User user = new User();
        user.setName(userDto.name());
        user.setUsername(userDto.username());
        user.setEmail(userDto.email());
        user.setPassword(userDto.password());
        userRepository.save(user);
        return ResponseEntity.ok(new DataResponse<>("User created successfully", 200));
    }

}
