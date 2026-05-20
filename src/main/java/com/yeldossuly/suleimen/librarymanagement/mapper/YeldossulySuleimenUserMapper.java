package com.yeldossuly.suleimen.librarymanagement.mapper;

import com.yeldossuly.suleimen.librarymanagement.dto.UserResponseDto;
import com.yeldossuly.suleimen.librarymanagement.entity.Role;
import com.yeldossuly.suleimen.librarymanagement.entity.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class YeldossulySuleimenUserMapper {

    public UserResponseDto toDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getRoles().stream()
                        .map(Role::getName)
                        .map(Enum::name)
                        .collect(Collectors.toSet())
        );
    }
}
