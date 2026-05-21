package com.yeldossuly.suleimen.librarymanagement.dto;

public record LibrarySummaryDto(
        long books,
        long users,
        long borrowings,
        long reservations,
        long files
) {
}
