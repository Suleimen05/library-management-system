package com.yeldossuly.suleimen.librarymanagement.dto;

public record AuthResponseDto(
        String token,
        String tokenType,
        UserResponseDto user
) {
}
