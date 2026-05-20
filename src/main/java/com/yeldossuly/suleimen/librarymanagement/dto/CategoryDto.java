package com.yeldossuly.suleimen.librarymanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CategoryDto(
        Long id,

        @NotBlank(message = "Category name is required")
        @Size(max = 100, message = "Category name must be shorter than 100 characters")
        String name
) {
}
