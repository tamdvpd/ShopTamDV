package com.tamdvshop.products.price;

import com.tamdvshop.products.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductPriceHistoryRepository extends JpaRepository<ProductPriceHistory, Long> {
    List<ProductPriceHistory> findByProductOrderByChangedAtDesc(Product product);
}
