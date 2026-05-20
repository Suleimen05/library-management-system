package com.yeldossuly.suleimen.librarymanagement.repository;

import com.yeldossuly.suleimen.librarymanagement.entity.Role;
import com.yeldossuly.suleimen.librarymanagement.entity.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(UserRole name);
}
