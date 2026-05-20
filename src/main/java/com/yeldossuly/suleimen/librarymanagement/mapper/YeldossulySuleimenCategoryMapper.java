package com.yeldossuly.suleimen.librarymanagement.mapper;

import com.yeldossuly.suleimen.librarymanagement.dto.CategoryDto;
import com.yeldossuly.suleimen.librarymanagement.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class YeldossulySuleimenCategoryMapper {

    public CategoryDto toDto(Category category) {
        return new CategoryDto(category.getId(), category.getName());
    }

    public Category toEntity(CategoryDto dto) {
        Category category = new Category();
        category.setId(dto.id());
        category.setName(dto.name());
        return category;
    }

    public void updateEntity(Category category, CategoryDto dto) {
        category.setName(dto.name());
    }
}
