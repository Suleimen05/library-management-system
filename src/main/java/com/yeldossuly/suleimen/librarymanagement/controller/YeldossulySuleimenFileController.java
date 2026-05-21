package com.yeldossuly.suleimen.librarymanagement.controller;

import com.yeldossuly.suleimen.librarymanagement.dto.FileResourceDto;
import com.yeldossuly.suleimen.librarymanagement.entity.FileResource;
import com.yeldossuly.suleimen.librarymanagement.service.YeldossulySuleimenFileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class YeldossulySuleimenFileController {

    private final YeldossulySuleimenFileStorageService fileStorageService;

    @PostMapping(value = "/api/books/{bookId}/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public FileResourceDto uploadBookFile(
            @PathVariable Long bookId,
            @RequestParam("file") MultipartFile file
    ) {
        return fileStorageService.uploadBookFile(bookId, file);
    }

    @GetMapping("/api/books/{bookId}/files")
    public List<FileResourceDto> getBookFiles(@PathVariable Long bookId) {
        return fileStorageService.getBookFiles(bookId);
    }

    @GetMapping("/api/files/{id}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long id) {
        FileResource fileResource = fileStorageService.findFile(id);
        Resource resource = fileStorageService.loadFileAsResource(fileResource);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileResource.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileResource.getOriginalName() + "\"")
                .body(resource);
    }
}
