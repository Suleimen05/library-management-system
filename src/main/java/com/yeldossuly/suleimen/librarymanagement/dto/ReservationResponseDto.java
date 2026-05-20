package com.yeldossuly.suleimen.librarymanagement.dto;

import com.yeldossuly.suleimen.librarymanagement.entity.enums.ReservationStatus;

import java.time.LocalDateTime;

public record ReservationResponseDto(
        Long id,
        Long bookId,
        String bookTitle,
        Long userId,
        String userEmail,
        LocalDateTime reservedAt,
        ReservationStatus status
) {
}
