package com.tamdvshop.productimages;

import com.tamdvshop.products.Product;
import com.tamdvshop.products.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductImageService {

    private final ProductImageRepository repository;
    private final ProductRepository productRepository;

    @Transactional
    public ProductImageResponse addImage(ProductImageRequest request) {
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        ProductImage image = ProductImage.builder()
                .product(product)
                .imageUrl(request.imageUrl())
                .build();
        return ProductImageMapper.toResponse(repository.save(image));
    }

    @Transactional(readOnly = true)
    public List<ProductImageResponse> findByProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        return repository.findByProduct(product)
                .stream()
                .map(ProductImageMapper::toResponse)
                .collect(Collectors.toList());
    }
}
