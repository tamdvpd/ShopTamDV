package com.example.shop.controller;

import com.example.shop.dto.CategoryDTO;
import com.example.shop.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/categories")
    public Page<CategoryDTO> getCategories(Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @PostMapping("/admin/categories")
    public CategoryDTO createCategory(@Valid @RequestBody CategoryDTO dto) {
        return categoryService.create(dto);
    }

    @PutMapping("/admin/categories/{id}")
    public CategoryDTO updateCategory(@PathVariable Long id, @Valid @RequestBody CategoryDTO dto) {
        return categoryService.update(id, dto);
    }

    @DeleteMapping("/admin/categories/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.delete(id);
    }
}
