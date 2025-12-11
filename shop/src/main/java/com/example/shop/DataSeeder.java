package com.example.shop;

import com.example.shop.category.Category;
import com.example.shop.product.Product;
import com.example.shop.user.Role;
import com.example.shop.user.User;
import com.example.shop.repository.CategoryRepository;
import com.example.shop.repository.ProductRepository;
import com.example.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        seedAdmin();
        if (categoryRepository.count() > 0 || productRepository.count() > 0) {
            return;
        }
        Category c1 = new Category();
        c1.setName("Books");
        Category c2 = new Category();
        c2.setName("Electronics");
        Category c3 = new Category();
        c3.setName("Clothes");
        categoryRepository.saveAll(List.of(c1, c2, c3));

        productRepository.saveAll(List.of(
            createProduct("Book A", BigDecimal.valueOf(10), 100, c1),
            createProduct("Book B", BigDecimal.valueOf(12), 80, c1),
            createProduct("Phone", BigDecimal.valueOf(500), 50, c2),
            createProduct("Laptop", BigDecimal.valueOf(1000), 30, c2),
            createProduct("Shirt", BigDecimal.valueOf(20), 200, c3),
            createProduct("Jeans", BigDecimal.valueOf(40), 150, c3)
        ));

        log.info("Seeded demo data");
    }

    private void seedAdmin() {
//        if (userRepository.existsByUsername("admin")) {
//            return;
//        }
//        Role role = roleRepository.findByName("ROLE_ADMIN")
//                .orElseGet(() -> roleRepository.save(new Role(null, "ROLE_ADMIN")));
//        User user = new User();
//        user.setUsername("admin");
//        user.setPassword(passwordEncoder.encode("admin"));
//        user.setRoles(Set.of(role));
//        userRepository.save(user);
    }

    private Product createProduct(String name, BigDecimal price, int stock, Category category) {
        Product p = new Product();
        p.setName(name);
        p.setPrice(price);
        p.setStock(stock);
        p.setCategory(category);
        return p;
    }
}
