package com.dako.forohub.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dako.forohub.user.domain.User;
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByIsActiveTrue();

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    User findByUsername(String username); // Agrega este método
}
