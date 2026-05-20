package com.yeldossuly.suleimen.librarymanagement.service;

import com.yeldossuly.suleimen.librarymanagement.dto.AuthorDto;
import com.yeldossuly.suleimen.librarymanagement.entity.Author;
import com.yeldossuly.suleimen.librarymanagement.mapper.YeldossulySuleimenAuthorMapper;
import com.yeldossuly.suleimen.librarymanagement.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class YeldossulySuleimenAuthorService {

    private final AuthorRepository authorRepository;
    private final YeldossulySuleimenAuthorMapper authorMapper;

    @Transactional(readOnly = true)
    public List<AuthorDto> getAllAuthors() {
        return authorRepository.findAll().stream()
                .map(authorMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public AuthorDto getAuthorById(Long id) {
        return authorMapper.toDto(findAuthor(id));
    }

    @Transactional
    public AuthorDto createAuthor(AuthorDto request) {
        Author author = authorMapper.toEntity(request);
        author.setId(null);
        return authorMapper.toDto(authorRepository.save(author));
    }

    @Transactional
    public AuthorDto updateAuthor(Long id, AuthorDto request) {
        Author author = findAuthor(id);
        authorMapper.updateEntity(author, request);
        return authorMapper.toDto(authorRepository.save(author));
    }

    @Transactional
    public void deleteAuthor(Long id) {
        Author author = findAuthor(id);
        authorRepository.delete(author);
    }

    private Author findAuthor(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found"));
    }
}
