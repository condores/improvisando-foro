package com.dako.forohub.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dako.forohub.authentication.service.PasswordValidationService;
import com.dako.forohub.infra.exceptions.DuplicateResourceException;
import com.dako.forohub.user.domain.Role;
import com.dako.forohub.user.domain.RolesEnum;
import com.dako.forohub.user.domain.User;
import com.dako.forohub.user.dtos.UserRegisterRequestDto;
import com.dako.forohub.user.repositories.RoleRepository;
import com.dako.forohub.user.repositories.UserRepository;

@Service
public class CreateUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordValidationService passwordValidationService;

    public CreateUserService(UserRepository userRepository, RoleRepository roleRepository,
            PasswordValidationService passwordValidationService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordValidationService = passwordValidationService;
    }

    @Transactional
    public void createUser(UserRegisterRequestDto userDto) {
        validateUserUniqueness(userDto);
        passwordValidationService.validatePassword(new String(userDto.password()));

        User user = mapToUser(userDto);
        assignDefaultRole(user);

        userRepository.save(user);
    }

    private void validateUserUniqueness(UserRegisterRequestDto userDto) {
        if (userRepository.existsByEmail(userDto.email())) {
            throw new DuplicateResourceException("Email already exists");
        }
        if (userRepository.existsByUsername(userDto.username())) {
            throw new DuplicateResourceException("Username already exists");
        }
    }

    private User mapToUser(UserRegisterRequestDto userDto) {
        User user = new User();
        user.setName(userDto.name());
        user.setUsername(userDto.username());
        user.setEmail(userDto.email());
        user.setPassword(userDto.password().toCharArray());
        return user;
    }

    private void assignDefaultRole(User user) {
        Role defaultRole = roleRepository.findByName(RolesEnum.USER);
        user.getRoles().add(defaultRole);
    }
}
