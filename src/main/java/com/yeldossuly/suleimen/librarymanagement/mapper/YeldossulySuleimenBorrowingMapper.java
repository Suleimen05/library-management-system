package com.yeldossuly.suleimen.librarymanagement.mapper;

import com.yeldossuly.suleimen.librarymanagement.dto.BorrowingResponseDto;
import com.yeldossuly.suleimen.librarymanagement.entity.Borrowing;
import org.springframework.stereotype.Component;

@Component
public class YeldossulySuleimenBorrowingMapper {

    public BorrowingResponseDto toDto(Borrowing borrowing) {
        return new BorrowingResponseDto(
                borrowing.getId(),
                borrowing.getBook().getId(),
                borrowing.getBook().getTitle(),
                borrowing.getUser().getId(),
                borrowing.getUser().getEmail(),
                borrowing.getBorrowedAt(),
                borrowing.getDueDate(),
                borrowing.getReturnedAt(),
                borrowing.getStatus()
        );
    }
}
