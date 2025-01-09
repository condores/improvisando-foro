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

@RestController
@RequestMapping("admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminUserService adminUserService;

    public AdminController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    @GetMapping("users")
    public ResponseEntity<DataResponse<Page<UserInfoDto>>> getAllUsers(
            @PageableDefault(size = 5, page = 0) Pageable pageable) {
        Page<UserInfoDto> infoUserPage = adminUserService.getAllActiveUsers(pageable);
        return ResponseEntity.ok(new DataResponse<>("Users retrieved successfully", 200, infoUserPage));
    }
}
