package com.yeldossuly.suleimen.librarymanagement.repository;

import com.yeldossuly.suleimen.librarymanagement.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findByUserId(Long userId);
}
