package com.tamdvshop.products;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-variants")
@RequiredArgsConstructor
public class ProductVariantController {

    private final ProductVariantService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductVariantResponse create(@Valid @RequestBody ProductVariantRequest request) {
        return service.create(request);
    }

    @GetMapping("/product/{productId}")
    public List<ProductVariantResponse> byProduct(@PathVariable Long productId) {
        return service.findByProduct(productId);
    }

    @PutMapping("/{id}")
    public ProductVariantResponse update(@PathVariable Long id, @Valid @RequestBody ProductVariantRequest request) {
        return service.update(id, request);
    }
}
