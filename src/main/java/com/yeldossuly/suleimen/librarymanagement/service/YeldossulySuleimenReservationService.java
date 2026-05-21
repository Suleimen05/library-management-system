package com.yeldossuly.suleimen.librarymanagement.service;

import com.yeldossuly.suleimen.librarymanagement.dto.ReservationResponseDto;
import com.yeldossuly.suleimen.librarymanagement.entity.Book;
import com.yeldossuly.suleimen.librarymanagement.entity.Reservation;
import com.yeldossuly.suleimen.librarymanagement.entity.User;
import com.yeldossuly.suleimen.librarymanagement.entity.enums.ReservationStatus;
import com.yeldossuly.suleimen.librarymanagement.exception.YeldossulySuleimenBadRequestException;
import com.yeldossuly.suleimen.librarymanagement.exception.YeldossulySuleimenResourceNotFoundException;
import com.yeldossuly.suleimen.librarymanagement.mapper.YeldossulySuleimenReservationMapper;
import com.yeldossuly.suleimen.librarymanagement.repository.BookRepository;
import com.yeldossuly.suleimen.librarymanagement.repository.ReservationRepository;
import com.yeldossuly.suleimen.librarymanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class YeldossulySuleimenReservationService {

    private final ReservationRepository reservationRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final YeldossulySuleimenReservationMapper reservationMapper;

    @Transactional
    public ReservationResponseDto reserveBook(Long bookId, Long userId) {
        Book book = findBook(bookId);
        User user = findUser(userId);

        Reservation reservation = new Reservation();
        reservation.setBook(book);
        reservation.setUser(user);
        reservation.setReservedAt(LocalDateTime.now());
        reservation.setStatus(ReservationStatus.ACTIVE);

        return reservationMapper.toDto(reservationRepository.save(reservation));
    }

    @Transactional
    public ReservationResponseDto cancelReservation(Long reservationId) {
        Reservation reservation = findReservation(reservationId);

        if (reservation.getStatus() == ReservationStatus.CANCELLED) {
            throw new YeldossulySuleimenBadRequestException("Reservation already cancelled");
        }

        reservation.setStatus(ReservationStatus.CANCELLED);
        return reservationMapper.toDto(reservationRepository.save(reservation));
    }

    @Transactional(readOnly = true)
    public List<ReservationResponseDto> getReservationsByUser(Long userId) {
        findUser(userId);
        return reservationRepository.findByUserId(userId).stream()
                .map(reservationMapper::toDto)
                .toList();
    }

    private Reservation findReservation(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new YeldossulySuleimenResourceNotFoundException("Reservation not found"));
    }

    private Book findBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new YeldossulySuleimenResourceNotFoundException("Book not found"));
    }

    private User findUser(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new YeldossulySuleimenResourceNotFoundException("User not found"));
    }
}
