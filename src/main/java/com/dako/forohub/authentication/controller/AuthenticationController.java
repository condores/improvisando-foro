package com.dako.forohub.authentication.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dako.forohub.authentication.dto.LoginRequestDto;
import com.dako.forohub.infra.responses.DataResponse;
import com.dako.forohub.infra.security.JWTToken;
import com.dako.forohub.infra.security.TokenService;
import com.dako.forohub.user.domain.User;
import com.dako.forohub.user.repositories.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserRepository userRepository;

    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService,
            UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequestDto loginRequestDto) {
        logger.info("Intento de login para el usuario: {}", loginRequestDto.username());
        try {
            // Verificar si el usuario existe
            User user = userRepository.findByUsername(loginRequestDto.username());
            if (user == null) {
                logger.error("Usuario no encontrado: {}", loginRequestDto.username());
                return ResponseEntity.badRequest().body(new DataResponse<>("Usuario no encontrado", 400, null));
            }

            // Intentar autenticar
            Authentication authToken = new UsernamePasswordAuthenticationToken(loginRequestDto.username(),
                    loginRequestDto.password());
            var userAuthenticated = authenticationManager.authenticate(authToken);

            logger.info("Usuario autenticado exitosamente: {}", loginRequestDto.username());
            var JWTtoken = tokenService.generateToken((User) userAuthenticated.getPrincipal());
            return ResponseEntity.ok(new DataResponse<>("Login exitoso", 200, new JWTToken(JWTtoken)));
        } catch (BadCredentialsException e) {
            logger.error("Credenciales inválidas para el usuario: {}", loginRequestDto.username(), e);
            return ResponseEntity.badRequest().body(new DataResponse<>("Credenciales inválidas", 400, null));
        } catch (Exception e) {
            logger.error("Error inesperado durante el login para el usuario: {}", loginRequestDto.username(), e);
            return ResponseEntity.internalServerError()
                    .body(new DataResponse<>("Error interno del servidor", 500, null));
        }
    }
}
