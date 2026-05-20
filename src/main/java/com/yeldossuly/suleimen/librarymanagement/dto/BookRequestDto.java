package com.yeldossuly.suleimen.librarymanagement.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BookRequestDto(
        @NotBlank(message = "Title is required")
        @Size(max = 255, message = "Title must be shorter than 255 characters")
        String title,

        @NotBlank(message = "ISBN is required")
        @Size(max = 50, message = "ISBN must be shorter than 50 characters")
        String isbn,

        @Size(max = 1500, message = "Description must be shorter than 1500 characters")
        String description,

        @NotNull(message = "Publication year is required")
        @Min(value = 1000, message = "Publication year must be valid")
        Integer publicationYear,

        @NotNull(message = "Total copies is required")
        @Min(value = 0, message = "Total copies cannot be negative")
        Integer totalCopies,

        @NotNull(message = "Available copies is required")
        @Min(value = 0, message = "Available copies cannot be negative")
        Integer availableCopies,

        @NotNull(message = "Author id is required")
        Long authorId,

        @NotNull(message = "Category id is required")
        Long categoryId
) {
}
