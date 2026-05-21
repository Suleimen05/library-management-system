package com.yeldossuly.suleimen.librarymanagement.service;

import com.yeldossuly.suleimen.librarymanagement.dto.FileResourceDto;
import com.yeldossuly.suleimen.librarymanagement.entity.Book;
import com.yeldossuly.suleimen.librarymanagement.entity.FileResource;
import com.yeldossuly.suleimen.librarymanagement.exception.YeldossulySuleimenBadRequestException;
import com.yeldossuly.suleimen.librarymanagement.exception.YeldossulySuleimenResourceNotFoundException;
import com.yeldossuly.suleimen.librarymanagement.mapper.YeldossulySuleimenFileResourceMapper;
import com.yeldossuly.suleimen.librarymanagement.repository.BookRepository;
import com.yeldossuly.suleimen.librarymanagement.repository.FileResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class YeldossulySuleimenFileStorageService {

    private final FileResourceRepository fileResourceRepository;
    private final BookRepository bookRepository;
    private final YeldossulySuleimenFileResourceMapper fileResourceMapper;

    @Value("${app.file.upload-dir}")
    private String uploadDir;

    @Transactional
    public FileResourceDto uploadBookFile(Long bookId, MultipartFile file) {
        if (file.isEmpty()) {
            throw new YeldossulySuleimenBadRequestException("File cannot be empty");
        }

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new YeldossulySuleimenResourceNotFoundException("Book not found"));

        try {
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);

            String originalName = file.getOriginalFilename() == null ? "file" : file.getOriginalFilename();
            String storedName = UUID.randomUUID() + "-" + originalName.replaceAll("[^a-zA-Z0-9._-]", "_");
            Path targetPath = uploadPath.resolve(storedName);

            file.transferTo(targetPath);

            FileResource fileResource = new FileResource();
            fileResource.setOriginalName(originalName);
            fileResource.setStoredName(storedName);
            fileResource.setContentType(file.getContentType() == null ? "application/octet-stream" : file.getContentType());
            fileResource.setSize(file.getSize());
            fileResource.setPath(targetPath.toString());
            fileResource.setUploadedAt(LocalDateTime.now());
            fileResource.setBook(book);

            return fileResourceMapper.toDto(fileResourceRepository.save(fileResource));
        } catch (IOException exception) {
            throw new YeldossulySuleimenBadRequestException("Could not store file");
        }
    }

    @Transactional(readOnly = true)
    public List<FileResourceDto> getBookFiles(Long bookId) {
        if (!bookRepository.existsById(bookId)) {
            throw new YeldossulySuleimenResourceNotFoundException("Book not found");
        }

        return fileResourceRepository.findByBookId(bookId).stream()
                .map(fileResourceMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public FileResource findFile(Long id) {
        return fileResourceRepository.findById(id)
                .orElseThrow(() -> new YeldossulySuleimenResourceNotFoundException("File not found"));
    }

    public Resource loadFileAsResource(FileResource fileResource) {
        try {
            Path filePath = Paths.get(fileResource.getPath()).toAbsolutePath().normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists() || !resource.isReadable()) {
                throw new YeldossulySuleimenResourceNotFoundException("Stored file not found");
            }

            return resource;
        } catch (MalformedURLException exception) {
            throw new YeldossulySuleimenResourceNotFoundException("Stored file not found");
        }
    }
}
