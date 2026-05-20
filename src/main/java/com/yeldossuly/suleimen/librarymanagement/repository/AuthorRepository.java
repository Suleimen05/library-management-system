package com.yeldossuly.suleimen.librarymanagement.repository;

import com.yeldossuly.suleimen.librarymanagement.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
