package com.yeldossuly.suleimen.librarymanagement.service;

import com.yeldossuly.suleimen.librarymanagement.dto.CategoryDto;
import com.yeldossuly.suleimen.librarymanagement.entity.Category;
import com.yeldossuly.suleimen.librarymanagement.mapper.YeldossulySuleimenCategoryMapper;
import com.yeldossuly.suleimen.librarymanagement.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class YeldossulySuleimenCategoryService {

    private final CategoryRepository categoryRepository;
    private final YeldossulySuleimenCategoryMapper categoryMapper;

    @Transactional(readOnly = true)
    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoryDto getCategoryById(Long id) {
        return categoryMapper.toDto(findCategory(id));
    }

    @Transactional
    public CategoryDto createCategory(CategoryDto request) {
        if (categoryRepository.existsByNameIgnoreCase(request.name())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Category with this name already exists");
        }

        Category category = categoryMapper.toEntity(request);
        category.setId(null);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Transactional
    public CategoryDto updateCategory(Long id, CategoryDto request) {
        Category category = findCategory(id);
        categoryRepository.findByNameIgnoreCase(request.name())
                .filter(existingCategory -> !existingCategory.getId().equals(id))
                .ifPresent(existingCategory -> {
                    throw new ResponseStatusException(HttpStatus.CONFLICT, "Category with this name already exists");
                });

        categoryMapper.updateEntity(category, request);
        return categoryMapper.toDto(categoryRepository.save(category));
    }

    @Transactional
    public void deleteCategory(Long id) {
        Category category = findCategory(id);
        categoryRepository.delete(category);
    }

    private Category findCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
    }
}
