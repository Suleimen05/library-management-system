package com.yeldossuly.suleimen.librarymanagement.dto;

import java.time.LocalDateTime;

public record FileResourceDto(
        Long id,
        String originalName,
        String contentType,
        Long size,
        LocalDateTime uploadedAt,
        Long bookId
) {
}
