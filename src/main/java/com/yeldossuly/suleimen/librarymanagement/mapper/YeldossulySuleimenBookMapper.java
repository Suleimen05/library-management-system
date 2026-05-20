package com.yeldossuly.suleimen.librarymanagement.mapper;

import com.yeldossuly.suleimen.librarymanagement.dto.BookRequestDto;
import com.yeldossuly.suleimen.librarymanagement.dto.BookResponseDto;
import com.yeldossuly.suleimen.librarymanagement.entity.Author;
import com.yeldossuly.suleimen.librarymanagement.entity.Book;
import com.yeldossuly.suleimen.librarymanagement.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class YeldossulySuleimenBookMapper {

    public BookResponseDto toDto(Book book) {
        return new BookResponseDto(
                book.getId(),
                book.getTitle(),
                book.getIsbn(),
                book.getDescription(),
                book.getPublicationYear(),
                book.getTotalCopies(),
                book.getAvailableCopies(),
                book.getAuthor().getId(),
                book.getAuthor().getFullName(),
                book.getCategory().getId(),
                book.getCategory().getName()
        );
    }

    public Book toEntity(BookRequestDto dto, Author author, Category category) {
        Book book = new Book();
        updateEntity(book, dto, author, category);
        return book;
    }

    public void updateEntity(Book book, BookRequestDto dto, Author author, Category category) {
        book.setTitle(dto.title());
        book.setIsbn(dto.isbn());
        book.setDescription(dto.description());
        book.setPublicationYear(dto.publicationYear());
        book.setTotalCopies(dto.totalCopies());
        book.setAvailableCopies(dto.availableCopies());
        book.setAuthor(author);
        book.setCategory(category);
    }
}
