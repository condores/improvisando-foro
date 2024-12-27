package com.dako.forohub.user.service;

import org.springframework.stereotype.Service;

import com.dako.forohub.exceptions.DuplicateResourceException;
import com.dako.forohub.user.domain.Role;
import com.dako.forohub.user.domain.RolesEnum;
import com.dako.forohub.user.domain.User;
import com.dako.forohub.user.dto.UserRegisterRequestDto;
import com.dako.forohub.user.repository.RoleRepository;
import com.dako.forohub.user.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public void createUser(UserRegisterRequestDto userDto) {
        if (userRepository.existsByEmail(userDto.email())) {
            throw new DuplicateResourceException("Email already exists");
        }
        if (userRepository.existsByUsername(userDto.username())) {
            throw new DuplicateResourceException("Username already exists");
        }
        if (userDto.password().length < 8 ||
                !new String(userDto.password()).matches(".*[A-Z].*") ||
                !new String(userDto.password()).matches(".*\\d.*")) {
            throw new IllegalArgumentException(
                    "Password must be at least 8 characters long, contain at least one uppercase letter and one number");
        }
        User user = new User();
        user.setName(userDto.name());
        user.setUsername(userDto.username());
        user.setEmail(userDto.email());
        user.setPassword(userDto.password());

        // Asignar rol por defecto
        Role defaultRole = roleRepository.findByName(RolesEnum.USER);
        user.getRoles().add(defaultRole);

        userRepository.save(user);
    }
}