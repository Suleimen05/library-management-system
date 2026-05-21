package com.yeldossuly.suleimen.librarymanagement.service;

import com.yeldossuly.suleimen.librarymanagement.dto.LibrarySummaryDto;
import com.yeldossuly.suleimen.librarymanagement.repository.BookRepository;
import com.yeldossuly.suleimen.librarymanagement.repository.BorrowingRepository;
import com.yeldossuly.suleimen.librarymanagement.repository.FileResourceRepository;
import com.yeldossuly.suleimen.librarymanagement.repository.ReservationRepository;
import com.yeldossuly.suleimen.librarymanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class YeldossulySuleimenAsyncService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BorrowingRepository borrowingRepository;
    private final ReservationRepository reservationRepository;
    private final FileResourceRepository fileResourceRepository;

    @Async
    public void sendWelcomeMessage(String email) {
        log.info("Async welcome message prepared for {}", email);
    }

    @Async
    public void processUploadedFile(Long fileId, String originalName) {
        log.info("Async file processing started for file id {} and name {}", fileId, originalName);
    }

    @Async
    public CompletableFuture<LibrarySummaryDto> buildLibrarySummary() {
        LibrarySummaryDto summary = new LibrarySummaryDto(
                bookRepository.count(),
                userRepository.count(),
                borrowingRepository.count(),
                reservationRepository.count(),
                fileResourceRepository.count()
        );

        return CompletableFuture.completedFuture(summary);
    }
}
