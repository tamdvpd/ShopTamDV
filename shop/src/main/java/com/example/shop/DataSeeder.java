package com.example.shop;

import com.example.shop.entity.Category;
import com.example.shop.entity.Product;
import com.example.shop.repository.CategoryRepository;
import com.example.shop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    @Override
    public void run(String... args) {
        if (categoryRepository.count() > 0 || productRepository.count() > 0) {
            return;
        }
        Category c1 = new Category();
        c1.setName("Books");
        c1.setSlug("books");
        Category c2 = new Category();
        c2.setName("Electronics");
        c2.setSlug("electronics");
        Category c3 = new Category();
        c3.setName("Clothes");
        c3.setSlug("clothes");
        categoryRepository.saveAll(List.of(c1, c2, c3));

        productRepository.saveAll(List.of(
            createProduct("Book A", "book-a", BigDecimal.valueOf(10), 100, c1),
            createProduct("Book B", "book-b", BigDecimal.valueOf(12), 80, c1),
            createProduct("Phone", "phone", BigDecimal.valueOf(500), 50, c2),
            createProduct("Laptop", "laptop", BigDecimal.valueOf(1000), 30, c2),
            createProduct("Shirt", "shirt", BigDecimal.valueOf(20), 200, c3),
            createProduct("Jeans", "jeans", BigDecimal.valueOf(40), 150, c3)
        ));

        log.info("Seeded demo data");
    }

    private Product createProduct(String name, String slug, BigDecimal price, int stock, Category category) {
        Product p = new Product();
        p.setName(name);
        p.setSlug(slug);
        p.setPrice(price);
        p.setStock(stock);
        p.setCategory(category);
        return p;
    }
}
