package com.dako.forohub.admin.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dako.forohub.user.dtos.UserInfoDto;
import com.dako.forohub.user.repositories.UserRepository;

@Service
public class AdminUserService {

    public AdminUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final UserRepository userRepository;

    public Page<UserInfoDto> getAllActiveUsers(Pageable pageable) {
        var users = userRepository.findAllByIsActiveTrue();
        List<UserInfoDto> infoUsersList = users.stream().map(UserInfoDto::new).collect(Collectors.toList());

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), infoUsersList.size());
        return new PageImpl<>(infoUsersList.subList(start, end), pageable, infoUsersList.size());
    }

}
