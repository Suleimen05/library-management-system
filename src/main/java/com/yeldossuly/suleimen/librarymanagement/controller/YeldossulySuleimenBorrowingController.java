package com.yeldossuly.suleimen.librarymanagement.controller;

import com.yeldossuly.suleimen.librarymanagement.dto.BorrowingResponseDto;
import com.yeldossuly.suleimen.librarymanagement.service.YeldossulySuleimenBorrowingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class YeldossulySuleimenBorrowingController {

    private final YeldossulySuleimenBorrowingService borrowingService;

    @PostMapping("/api/books/{bookId}/borrow")
    public ResponseEntity<BorrowingResponseDto> borrowBook(
            @PathVariable Long bookId,
            @RequestParam Long userId
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(borrowingService.borrowBook(bookId, userId));
    }

    @PutMapping("/api/borrowings/{id}/return")
    public BorrowingResponseDto returnBook(@PathVariable Long id) {
        return borrowingService.returnBook(id);
    }

    @GetMapping("/api/users/{userId}/borrowings")
    public List<BorrowingResponseDto> getUserBorrowings(@PathVariable Long userId) {
        return borrowingService.getBorrowingsByUser(userId);
    }
}
