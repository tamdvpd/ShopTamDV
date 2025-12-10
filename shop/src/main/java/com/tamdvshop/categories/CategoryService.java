package com.tamdvshop.categories;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
    }

    @Transactional
    public Category create(Category category) {
        categoryRepository.findByNameIgnoreCase(category.getName())
                .ifPresent(existing -> {
                    throw new IllegalArgumentException("Category already exists");
                });
        return categoryRepository.save(category);
    }

    @Transactional
    public Category update(Long id, Category updateRequest) {
        Category category = findById(id);
        category.setName(updateRequest.getName());
        category.setDescription(updateRequest.getDescription());
        return categoryRepository.save(category);
    }

    @Transactional
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
