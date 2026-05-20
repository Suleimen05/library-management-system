package com.yeldossuly.suleimen.librarymanagement.mapper;

import com.yeldossuly.suleimen.librarymanagement.dto.ReservationResponseDto;
import com.yeldossuly.suleimen.librarymanagement.entity.Reservation;
import org.springframework.stereotype.Component;

@Component
public class YeldossulySuleimenReservationMapper {

    public ReservationResponseDto toDto(Reservation reservation) {
        return new ReservationResponseDto(
                reservation.getId(),
                reservation.getBook().getId(),
                reservation.getBook().getTitle(),
                reservation.getUser().getId(),
                reservation.getUser().getEmail(),
                reservation.getReservedAt(),
                reservation.getStatus()
        );
    }
}
