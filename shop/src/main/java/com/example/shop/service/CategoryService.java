package com.example.shop.service;

import com.example.shop.dto.CategoryDTO;
import com.example.shop.entity.Category;
import com.example.shop.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Page<CategoryDTO> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(this::toDto);
    }

    public CategoryDTO create(CategoryDTO dto) {
        if (categoryRepository.existsBySlug(dto.getSlug())) {
            throw new RuntimeException("Slug already exists");
        }
        Category category = new Category();
        category.setName(dto.getName());
        category.setSlug(dto.getSlug());
        return toDto(categoryRepository.save(category));
    }

    public CategoryDTO update(Long id, CategoryDTO dto) {
        Category category = categoryRepository.findById(id).orElseThrow();
        if (!category.getSlug().equals(dto.getSlug()) && categoryRepository.existsBySlug(dto.getSlug())) {
            throw new RuntimeException("Slug already exists");
        }
        category.setName(dto.getName());
        category.setSlug(dto.getSlug());
        return toDto(categoryRepository.save(category));
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    private CategoryDTO toDto(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setSlug(category.getSlug());
        return dto;
    }
}
