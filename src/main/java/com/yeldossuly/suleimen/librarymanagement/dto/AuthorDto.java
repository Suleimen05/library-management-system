package com.yeldossuly.suleimen.librarymanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthorDto(
        Long id,

        @NotBlank(message = "Author full name is required")
        @Size(max = 255, message = "Author full name must be shorter than 255 characters")
        String fullName,

        @Size(max = 1000, message = "Biography must be shorter than 1000 characters")
        String biography
) {
}
