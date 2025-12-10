package com.example.shop.order;

import com.example.shop.common.ApiResponse;
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
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Order>>> getOrders(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ApiResponse.success(orderService.getOrders(user)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Order>> placeOrder(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ApiResponse.success(orderService.placeOrder(user)));
    }
}
