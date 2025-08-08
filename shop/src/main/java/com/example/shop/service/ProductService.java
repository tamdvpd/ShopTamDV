package com.example.shop.service;

import com.example.shop.dto.ProductDTO;
import com.example.shop.entity.Category;
import com.example.shop.entity.Product;
import com.example.shop.entity.ProductImage;
import com.example.shop.repository.CategoryRepository;
import com.example.shop.repository.ProductImageRepository;
import com.example.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductImageRepository productImageRepository;

    public Page<ProductDTO> search(String keyword, Long categoryId, Pageable pageable) {
        return productRepository.search(keyword, categoryId, pageable).map(this::toDto);
    }

    public ProductDTO findById(Long id) {
        return productRepository.findById(id).map(this::toDto).orElseThrow();
    }

    public ProductDTO create(ProductDTO dto) {
        if (productRepository.existsBySlug(dto.getSlug())) {
            throw new RuntimeException("Slug already exists");
        }
        Product product = toEntity(new Product(), dto);
        return toDto(productRepository.save(product));
    }

    public ProductDTO update(Long id, ProductDTO dto) {
        Product product = productRepository.findById(id).orElseThrow();
        if (!product.getSlug().equals(dto.getSlug()) && productRepository.existsBySlug(dto.getSlug())) {
            throw new RuntimeException("Slug already exists");
        }
        productImageRepository.deleteByProductId(product.getId());
        product = toEntity(product, dto);
        return toDto(productRepository.save(product));
    }

    public void delete(Long id) {
        productRepository.deleteById(id);
        productImageRepository.deleteByProductId(id);
    }

    private Product toEntity(Product product, ProductDTO dto) {
        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow();
        product.setName(dto.getName());
        product.setSlug(dto.getSlug());
        product.setPrice(dto.getPrice());
        product.setStock(dto.getStock());
        product.setCategory(category);
        if (dto.getImageUrl() != null) {
            ProductImage image = new ProductImage();
            image.setUrl(dto.getImageUrl());
            image.setProduct(product);
            product.setImages(Collections.singletonList(image));
        } else {
            product.setImages(Collections.emptyList());
        }
        return product;
    }

    private ProductDTO toDto(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setSlug(product.getSlug());
        dto.setPrice(product.getPrice());
        dto.setStock(product.getStock());
        dto.setCategoryId(product.getCategory() != null ? product.getCategory().getId() : null);
        dto.setImageUrl(product.getImages().isEmpty() ? null : product.getImages().get(0).getUrl());
        return dto;
    }
}
