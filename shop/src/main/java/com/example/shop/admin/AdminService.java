package com.example.shop.admin;

import com.example.shop.repository.OrderRepository;
import com.example.shop.repository.UserRepository;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;

    public AdminStats getStats() {
        long userCount = userRepository.count();
        long orderCount = orderRepository.count();
        Instant now = Instant.now();
        Instant start = now.minusSeconds(30L * 24 * 60 * 60);
        BigDecimal revenue = orderRepository.totalBetween(start, now);
        return new AdminStats(userCount, orderCount, revenue);
    }
}
