package com.fitdashy.fitdashy_backend.repository;

import com.fitdashy.fitdashy_backend.model.ERole;
import com.fitdashy.fitdashy_backend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByName(ERole name);
}