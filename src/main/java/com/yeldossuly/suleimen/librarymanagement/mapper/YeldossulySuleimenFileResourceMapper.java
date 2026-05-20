package com.yeldossuly.suleimen.librarymanagement.mapper;

import com.yeldossuly.suleimen.librarymanagement.dto.FileResourceDto;
import com.yeldossuly.suleimen.librarymanagement.entity.FileResource;
import org.springframework.stereotype.Component;

@Component
public class YeldossulySuleimenFileResourceMapper {

    public FileResourceDto toDto(FileResource fileResource) {
        Long bookId = fileResource.getBook() == null ? null : fileResource.getBook().getId();
        return new FileResourceDto(
                fileResource.getId(),
                fileResource.getOriginalName(),
                fileResource.getContentType(),
                fileResource.getSize(),
                fileResource.getUploadedAt(),
                bookId
        );
    }
}
