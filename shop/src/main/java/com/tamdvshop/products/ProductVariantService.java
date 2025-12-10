package com.tamdvshop.products;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductVariantService {

    private final ProductVariantRepository repository;
    private final ProductRepository productRepository;

    @Transactional
    public ProductVariantResponse create(ProductVariantRequest request) {
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        ProductVariant variant = ProductVariant.builder()
                .product(product)
                .variantName(request.variantName())
                .stock(request.stock())
                .price(request.price())
                .build();
        return ProductVariantMapper.toResponse(repository.save(variant));
    }

    @Transactional(readOnly = true)
    public List<ProductVariantResponse> findByProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        return repository.findByProduct(product)
                .stream()
                .map(ProductVariantMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProductVariantResponse update(Long variantId, ProductVariantRequest request) {
        ProductVariant variant = repository.findById(variantId)
                .orElseThrow(() -> new IllegalArgumentException("Variant not found"));
        variant.setVariantName(request.variantName());
        variant.setStock(request.stock());
        variant.setPrice(request.price());
        return ProductVariantMapper.toResponse(repository.save(variant));
    }
}
