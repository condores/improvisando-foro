package com.dako.forohub.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dako.forohub.user.domain.Role;
import com.dako.forohub.user.domain.RolesEnum;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(RolesEnum name);

}
