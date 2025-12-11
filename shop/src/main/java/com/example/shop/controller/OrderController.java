package com.example.shop.controller;

import com.example.shop.order.Order;
import com.example.shop.service.OrderService;
import com.example.shop.user.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> list(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(orderService.getOrders(user));
    }

    @PostMapping
    public ResponseEntity<Order> placeOrder(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(orderService.placeOrder(user));
    }
}
