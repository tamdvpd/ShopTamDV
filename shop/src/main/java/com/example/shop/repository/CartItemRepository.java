package com.example.shop.repository;

import com.example.shop.cart.CartItem;
import com.example.shop.user.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByUser(User user);
    Optional<CartItem> findByUserIdAndProductId(Long userId, Long productId);
}
