package com.yeldossuly.suleimen.librarymanagement.dto;

public record BookResponseDto(
        Long id,
        String title,
        String isbn,
        String description,
        Integer publicationYear,
        Integer totalCopies,
        Integer availableCopies,
        Long authorId,
        String authorName,
        Long categoryId,
        String categoryName
) {
}
