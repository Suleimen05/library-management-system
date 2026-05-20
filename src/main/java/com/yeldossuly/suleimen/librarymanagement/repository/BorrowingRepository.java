package com.yeldossuly.suleimen.librarymanagement.repository;

import com.yeldossuly.suleimen.librarymanagement.entity.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {

    List<Borrowing> findByUserId(Long userId);
}
