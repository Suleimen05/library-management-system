package com.yeldossuly.suleimen.librarymanagement.service;

import com.yeldossuly.suleimen.librarymanagement.dto.BookRequestDto;
import com.yeldossuly.suleimen.librarymanagement.dto.BookResponseDto;
import com.yeldossuly.suleimen.librarymanagement.entity.Author;
import com.yeldossuly.suleimen.librarymanagement.entity.Book;
import com.yeldossuly.suleimen.librarymanagement.entity.Category;
import com.yeldossuly.suleimen.librarymanagement.exception.YeldossulySuleimenDuplicateResourceException;
import com.yeldossuly.suleimen.librarymanagement.exception.YeldossulySuleimenResourceNotFoundException;
import com.yeldossuly.suleimen.librarymanagement.mapper.YeldossulySuleimenBookMapper;
import com.yeldossuly.suleimen.librarymanagement.repository.AuthorRepository;
import com.yeldossuly.suleimen.librarymanagement.repository.BookRepository;
import com.yeldossuly.suleimen.librarymanagement.repository.CategoryRepository;
import jakarta.persistence.criteria.JoinType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class YeldossulySuleimenBookService {

    private static final int MAX_PAGE_SIZE = 50;
    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
            "id",
            "title",
            "isbn",
            "publicationYear",
            "totalCopies",
            "availableCopies"
    );

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;
    private final YeldossulySuleimenBookMapper bookMapper;

    @Transactional(readOnly = true)
    public Page<BookResponseDto> getAllBooks(
            int page,
            int size,
            String sort,
            String direction,
            String search,
            Long categoryId,
            Boolean available
    ) {
        Pageable pageable = createPageable(page, size, sort, direction);
        Specification<Book> specification = createBookSpecification(search, categoryId, available);

        return bookRepository.findAll(specification, pageable)
                .map(bookMapper::toDto);
    }

    @Transactional(readOnly = true)
    public BookResponseDto getBookById(Long id) {
        return bookMapper.toDto(findBook(id));
    }

    @Transactional
    public BookResponseDto createBook(BookRequestDto request) {
        if (bookRepository.existsByIsbn(request.isbn())) {
            throw new YeldossulySuleimenDuplicateResourceException("Book with this ISBN already exists");
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
                    throw new YeldossulySuleimenDuplicateResourceException("Book with this ISBN already exists");
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
                .orElseThrow(() -> new YeldossulySuleimenResourceNotFoundException("Book not found"));
    }

    private Author findAuthor(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new YeldossulySuleimenResourceNotFoundException("Author not found"));
    }

    private Category findCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new YeldossulySuleimenResourceNotFoundException("Category not found"));
    }

    private Pageable createPageable(int page, int size, String sort, String direction) {
        int validPage = Math.max(page, 0);
        int validSize = Math.max(1, Math.min(size, MAX_PAGE_SIZE));
        String validSort = ALLOWED_SORT_FIELDS.contains(sort) ? sort : "id";
        Sort.Direction validDirection = "desc".equalsIgnoreCase(direction)
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        return PageRequest.of(validPage, validSize, Sort.by(validDirection, validSort));
    }

    private Specification<Book> createBookSpecification(String search, Long categoryId, Boolean available) {
        return Specification.allOf(
                searchSpecification(search),
                categorySpecification(categoryId),
                availabilitySpecification(available)
        );
    }

    private Specification<Book> searchSpecification(String search) {
        return (root, query, criteriaBuilder) -> {
            if (search == null || search.isBlank()) {
                return criteriaBuilder.conjunction();
            }

            String pattern = "%" + search.trim().toLowerCase() + "%";
            return criteriaBuilder.or(
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("title")), pattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.get("isbn")), pattern),
                    criteriaBuilder.like(criteriaBuilder.lower(root.join("author", JoinType.LEFT).get("fullName")), pattern)
            );
        };
    }

    private Specification<Book> categorySpecification(Long categoryId) {
        return (root, query, criteriaBuilder) -> {
            if (categoryId == null) {
                return criteriaBuilder.conjunction();
            }

            return criteriaBuilder.equal(root.get("category").get("id"), categoryId);
        };
    }

    private Specification<Book> availabilitySpecification(Boolean available) {
        return (root, query, criteriaBuilder) -> {
            if (available == null) {
                return criteriaBuilder.conjunction();
            }

            if (available) {
                return criteriaBuilder.greaterThan(root.get("availableCopies"), 0);
            }

            return criteriaBuilder.equal(root.get("availableCopies"), 0);
        };
    }
}
