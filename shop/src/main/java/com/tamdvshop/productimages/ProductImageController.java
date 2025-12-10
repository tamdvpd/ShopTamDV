package com.tamdvshop.productimages;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-images")
@RequiredArgsConstructor
public class ProductImageController {

    private final ProductImageService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductImageResponse create(@Valid @RequestBody ProductImageRequest request) {
        return service.addImage(request);
    }

    @GetMapping("/product/{productId}")
    public List<ProductImageResponse> getByProduct(@PathVariable Long productId) {
        return service.findByProduct(productId);
    }
}
