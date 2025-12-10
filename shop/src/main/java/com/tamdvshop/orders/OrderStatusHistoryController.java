package com.tamdvshop.orders;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders/status-history")
@RequiredArgsConstructor
public class OrderStatusHistoryController {

    private final OrderStatusHistoryService service;
    private final OrderRepository orderRepository;

    @GetMapping("/{orderId}")
    public List<OrderStatusHistoryResponse> getHistory(@PathVariable Long orderId) {
        return service.getHistory(orderId, orderRepository);
    }
}
