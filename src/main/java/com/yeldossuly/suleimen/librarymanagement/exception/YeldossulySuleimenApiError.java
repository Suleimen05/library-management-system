package com.yeldossuly.suleimen.librarymanagement.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record YeldossulySuleimenApiError(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        Map<String, String> validationErrors
) {
}
