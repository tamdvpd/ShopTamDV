package com.tamdvshop.products.price;

import com.tamdvshop.products.Product;
import com.tamdvshop.products.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductPriceService {

    private final ProductRepository productRepository;
    private final ProductPriceHistoryRepository historyRepository;

    @Transactional
    public void updatePrice(Long productId, BigDecimal newPrice) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        BigDecimal oldPrice = product.getPrice();
        product.setPrice(newPrice);
        productRepository.save(product);

        ProductPriceHistory history = ProductPriceHistory.builder()
                .product(product)
                .oldPrice(oldPrice)
                .newPrice(newPrice)
                .build();
        historyRepository.save(history);
    }

    @Transactional(readOnly = true)
    public List<ProductPriceHistoryResponse> getHistory(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        return historyRepository.findByProductOrderByChangedAtDesc(product)
                .stream()
                .map(entry -> new ProductPriceHistoryResponse(entry.getId(), entry.getOldPrice(), entry.getNewPrice(), entry.getChangedAt()))
                .collect(Collectors.toList());
    }
}
