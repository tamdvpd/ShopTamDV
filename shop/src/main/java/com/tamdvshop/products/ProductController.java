package com.tamdvshop.products;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAll(@RequestParam(value = "categoryId", required = false) Long categoryId) {
        if (categoryId != null) {
            return ResponseEntity.ok(productService.findByCategory(categoryId));
        }
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> create(
            @RequestParam String name,
            @RequestParam(required = false) String description,
            @RequestParam BigDecimal price,
            @RequestParam(required = false) Integer stock,
            @RequestParam(required = false) Long categoryId,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        ProductRequest request = new ProductRequest(name, description, price, stock, categoryId);
        return ResponseEntity.ok(productService.create(request, image));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Product> update(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam(required = false) String description,
            @RequestParam BigDecimal price,
            @RequestParam(required = false) Integer stock,
            @RequestParam(required = false) Long categoryId,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        ProductRequest request = new ProductRequest(name, description, price, stock, categoryId);
        return ResponseEntity.ok(productService.update(id, request, image));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
