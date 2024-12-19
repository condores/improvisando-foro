package com.dako.forohub.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean // +
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {// +
        http// +
                .authorizeHttpRequests(authorize -> authorize// +
                        .anyRequest().permitAll() // Permitir todas las solicitudes sin autenticación//+
                )// +
                .csrf(csrf -> csrf.disable()); // Desactivar CSRF si es necesario//+
        return http.build();// +
    }
}