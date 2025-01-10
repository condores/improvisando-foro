package com.dako.forohub.admin.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dako.forohub.admin.service.AdminUserService;
import com.dako.forohub.infra.responses.DataResponse;
import com.dako.forohub.user.dtos.UserInfoDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("admin")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Admin", description = "Endpoints for admin operations")

public class AdminController {

    private final AdminUserService adminUserService;

    public AdminController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @GetMapping("users")
    @Operation(summary = "Get All Users", description = "Retrieve all active users with pagination",security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<DataResponse<Page<UserInfoDto>>> getAllUsers(
            @PageableDefault(size = 5, page = 0) Pageable pageable) {
        Page<UserInfoDto> infoUserPage = adminUserService.getAllActiveUsers(pageable);
        return ResponseEntity.ok(new DataResponse<>("Users retrieved successfully", 200, infoUserPage));
    }
}
