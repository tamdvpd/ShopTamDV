package com.example.shop.controller;

import com.example.shop.dto.ProductDTO;
import com.example.shop.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/products")
    public Page<ProductDTO> searchProducts(@RequestParam(required = false) String keyword,
                                           @RequestParam(required = false) Long categoryId,
                                           Pageable pageable) {
        return productService.search(keyword, categoryId, pageable);
    }

    @GetMapping("/products/{id}")
    public ProductDTO getProduct(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PostMapping("/admin/products")
    public ProductDTO createProduct(@Valid @RequestBody ProductDTO dto) {
        return productService.create(dto);
    }

    @PutMapping("/admin/products/{id}")
    public ProductDTO updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO dto) {
        return productService.update(id, dto);
    }

    @DeleteMapping("/admin/products/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.delete(id);
    }
}
