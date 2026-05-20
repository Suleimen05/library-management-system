package com.yeldossuly.suleimen.librarymanagement.dto;

import com.yeldossuly.suleimen.librarymanagement.entity.enums.BorrowingStatus;

import java.time.LocalDate;

public record BorrowingResponseDto(
        Long id,
        Long bookId,
        String bookTitle,
        Long userId,
        String userEmail,
        LocalDate borrowedAt,
        LocalDate dueDate,
        LocalDate returnedAt,
        BorrowingStatus status
) {
}
