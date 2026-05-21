package com.yeldossuly.suleimen.librarymanagement.service;

import com.yeldossuly.suleimen.librarymanagement.dto.BorrowingResponseDto;
import com.yeldossuly.suleimen.librarymanagement.entity.Book;
import com.yeldossuly.suleimen.librarymanagement.entity.Borrowing;
import com.yeldossuly.suleimen.librarymanagement.entity.User;
import com.yeldossuly.suleimen.librarymanagement.entity.enums.BorrowingStatus;
import com.yeldossuly.suleimen.librarymanagement.exception.YeldossulySuleimenBadRequestException;
import com.yeldossuly.suleimen.librarymanagement.exception.YeldossulySuleimenResourceNotFoundException;
import com.yeldossuly.suleimen.librarymanagement.mapper.YeldossulySuleimenBorrowingMapper;
import com.yeldossuly.suleimen.librarymanagement.repository.BookRepository;
import com.yeldossuly.suleimen.librarymanagement.repository.BorrowingRepository;
import com.yeldossuly.suleimen.librarymanagement.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class YeldossulySuleimenBorrowingService {

    private static final int BORROW_DAYS = 14;

    private final BorrowingRepository borrowingRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final YeldossulySuleimenBorrowingMapper borrowingMapper;

    @Transactional
    public BorrowingResponseDto borrowBook(Long bookId, Long userId) {
        Book book = findBook(bookId);
        User user = findUser(userId);

        if (book.getAvailableCopies() <= 0) {
            throw new YeldossulySuleimenBadRequestException("No available copies for this book");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);

        Borrowing borrowing = new Borrowing();
        borrowing.setBook(book);
        borrowing.setUser(user);
        borrowing.setBorrowedAt(LocalDate.now());
        borrowing.setDueDate(LocalDate.now().plusDays(BORROW_DAYS));
        borrowing.setStatus(BorrowingStatus.BORROWED);

        return borrowingMapper.toDto(borrowingRepository.save(borrowing));
    }

    @Transactional
    public BorrowingResponseDto returnBook(Long borrowingId) {
        Borrowing borrowing = findBorrowing(borrowingId);

        if (borrowing.getStatus() == BorrowingStatus.RETURNED) {
            throw new YeldossulySuleimenBadRequestException("Book already returned");
        }

        Book book = borrowing.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);

        borrowing.setReturnedAt(LocalDate.now());
        borrowing.setStatus(BorrowingStatus.RETURNED);

        return borrowingMapper.toDto(borrowingRepository.save(borrowing));
    }

    @Transactional(readOnly = true)
    public List<BorrowingResponseDto> getBorrowingsByUser(Long userId) {
        findUser(userId);
        return borrowingRepository.findByUserId(userId).stream()
                .map(borrowingMapper::toDto)
                .toList();
    }

    private Borrowing findBorrowing(Long id) {
        return borrowingRepository.findById(id)
                .orElseThrow(() -> new YeldossulySuleimenResourceNotFoundException("Borrowing not found"));
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
