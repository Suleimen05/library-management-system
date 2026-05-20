package com.yeldossuly.suleimen.librarymanagement.mapper;

import com.yeldossuly.suleimen.librarymanagement.dto.AuthorDto;
import com.yeldossuly.suleimen.librarymanagement.entity.Author;
import org.springframework.stereotype.Component;

@Component
public class YeldossulySuleimenAuthorMapper {

    public AuthorDto toDto(Author author) {
        return new AuthorDto(author.getId(), author.getFullName(), author.getBiography());
    }

    public Author toEntity(AuthorDto dto) {
        Author author = new Author();
        author.setId(dto.id());
        author.setFullName(dto.fullName());
        author.setBiography(dto.biography());
        return author;
    }

    public void updateEntity(Author author, AuthorDto dto) {
        author.setFullName(dto.fullName());
        author.setBiography(dto.biography());
    }
}
