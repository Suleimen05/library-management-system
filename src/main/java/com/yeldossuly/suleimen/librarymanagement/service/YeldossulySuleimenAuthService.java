package com.yeldossuly.suleimen.librarymanagement.service;

import com.yeldossuly.suleimen.librarymanagement.dto.AuthRegisterRequestDto;
import com.yeldossuly.suleimen.librarymanagement.dto.UserResponseDto;
import com.yeldossuly.suleimen.librarymanagement.entity.Role;
import com.yeldossuly.suleimen.librarymanagement.entity.User;
import com.yeldossuly.suleimen.librarymanagement.entity.enums.UserRole;
import com.yeldossuly.suleimen.librarymanagement.exception.YeldossulySuleimenDuplicateResourceException;
import com.yeldossuly.suleimen.librarymanagement.mapper.YeldossulySuleimenUserMapper;
import com.yeldossuly.suleimen.librarymanagement.repository.RoleRepository;
import com.yeldossuly.suleimen.librarymanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class YeldossulySuleimenAuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final YeldossulySuleimenUserMapper userMapper;

    @Transactional
    public UserResponseDto register(AuthRegisterRequestDto request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new YeldossulySuleimenDuplicateResourceException("User with this email already exists");
        }

        Role readerRole = roleRepository.findByName(UserRole.READER)
                .orElseGet(() -> roleRepository.save(new Role(null, UserRole.READER)));

        User user = new User();
        user.setFullName(request.fullName());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRoles(Set.of(readerRole));

        return userMapper.toDto(userRepository.save(user));
    }
}
