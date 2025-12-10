package com.tamdvshop.admin;

import com.tamdvshop.auth.UserRepository;
import com.tamdvshop.orders.Order;
import com.tamdvshop.orders.OrderRepository;
import com.tamdvshop.products.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public AdminStats getStats() {
        long totalOrders = orderRepository.count();
        BigDecimal revenue = orderRepository.findAll().stream()
                .map(Order::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        long totalProducts = productRepository.count();
        long totalUsers = userRepository.count();
        return new AdminStats(totalOrders, revenue, totalProducts, totalUsers);
    }
}
