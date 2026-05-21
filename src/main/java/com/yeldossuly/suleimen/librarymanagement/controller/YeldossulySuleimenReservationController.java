package com.yeldossuly.suleimen.librarymanagement.controller;

import com.yeldossuly.suleimen.librarymanagement.dto.ReservationResponseDto;
import com.yeldossuly.suleimen.librarymanagement.service.YeldossulySuleimenReservationService;
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
public class YeldossulySuleimenReservationController {

    private final YeldossulySuleimenReservationService reservationService;

    @PostMapping("/api/books/{bookId}/reserve")
    public ResponseEntity<ReservationResponseDto> reserveBook(
            @PathVariable Long bookId,
            @RequestParam Long userId
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.reserveBook(bookId, userId));
    }

    @PutMapping("/api/reservations/{id}/cancel")
    public ReservationResponseDto cancelReservation(@PathVariable Long id) {
        return reservationService.cancelReservation(id);
    }

    @GetMapping("/api/users/{userId}/reservations")
    public List<ReservationResponseDto> getUserReservations(@PathVariable Long userId) {
        return reservationService.getReservationsByUser(userId);
    }
}
