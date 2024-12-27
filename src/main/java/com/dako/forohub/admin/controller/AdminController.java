package com.dako.forohub.admin.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dako.forohub.response.DataResponse;
import com.dako.forohub.user.dto.InfoUsers;
import com.dako.forohub.user.repository.UserRepository;

@RestController
@RequestMapping("/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<DataResponse<List<InfoUsers>>> getAllUsers() {
        var users = userRepository.findAllByIsActiveTrue();
        List<InfoUsers> infoUsersList = users.stream()
                .map(InfoUsers::new) // Convertir cada User a InfoUsers
                .collect(Collectors.toList());
        return ResponseEntity.ok(new DataResponse<>("Users retrieved successfully", 200, infoUsersList));
    }

}
