package com.yeldossuly.suleimen.librarymanagement.dto;

import java.util.Set;

public record UserResponseDto(
        Long id,
        String fullName,
        String email,
        Set<String> roles
) {
}
