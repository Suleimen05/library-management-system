package com.yeldossuly.suleimen.librarymanagement.service;

import com.yeldossuly.suleimen.librarymanagement.dto.BookRequestDto;
import com.yeldossuly.suleimen.librarymanagement.dto.BookResponseDto;
import com.yeldossuly.suleimen.librarymanagement.entity.Author;
import com.yeldossuly.suleimen.librarymanagement.entity.Book;
import com.yeldossuly.suleimen.librarymanagement.entity.Category;
import com.yeldossuly.suleimen.librarymanagement.mapper.YeldossulySuleimenBookMapper;
import com.yeldossuly.suleimen.librarymanagement.repository.AuthorRepository;
import com.yeldossuly.suleimen.librarymanagement.repository.BookRepository;
import com.yeldossuly.suleimen.librarymanagement.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class YeldossulySuleimenBookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final YeldossulySuleimenBookMapper bookMapper;

    @Transactional(readOnly = true)
    public List<BookResponseDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public BookResponseDto getBookById(Long id) {
        return bookMapper.toDto(findBook(id));
    }

    @Transactional
    public BookResponseDto createBook(BookRequestDto request) {
        if (bookRepository.existsByIsbn(request.isbn())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Book with this ISBN already exists");
        }

        Author author = findAuthor(request.authorId());
        Category category = findCategory(request.categoryId());
        Book book = bookMapper.toEntity(request, author, category);

        return bookMapper.toDto(bookRepository.save(book));
    }

    @Transactional
    public BookResponseDto updateBook(Long id, BookRequestDto request) {
        Book book = findBook(id);
        bookRepository.findByIsbn(request.isbn())
                .filter(existingBook -> !existingBook.getId().equals(id))
                .ifPresent(existingBook -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Book with this ISBN already exists");
                });

        Author author = findAuthor(request.authorId());
        Category category = findCategory(request.categoryId());
        bookMapper.updateEntity(book, request, author, category);

        return bookMapper.toDto(bookRepository.save(book));
    }

    @Transactional
    public void deleteBook(Long id) {
        Book book = findBook(id);
        bookRepository.delete(book);
    }

    private Book findBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
    }

    private Author findAuthor(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));
    }

    private Category findCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
    }
}
