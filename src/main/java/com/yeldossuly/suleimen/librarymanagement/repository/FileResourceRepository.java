package com.yeldossuly.suleimen.librarymanagement.repository;

import com.yeldossuly.suleimen.librarymanagement.entity.FileResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileResourceRepository extends JpaRepository<FileResource, Long> {

    List<FileResource> findByBookId(Long bookId);
}
