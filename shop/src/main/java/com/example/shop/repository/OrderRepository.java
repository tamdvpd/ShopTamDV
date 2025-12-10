package com.example.shop.repository;

import com.example.shop.order.Order;
import com.example.shop.user.User;
import java.time.Instant;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);

    @Query("SELECT COALESCE(SUM(o.total),0) FROM Order o WHERE o.createdAt BETWEEN :from AND :to")
    java.math.BigDecimal totalBetween(Instant from, Instant to);
}
