package com.tamdvshop.orders;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderStatusHistoryService {

    private final OrderStatusHistoryRepository repository;

    @Transactional
    public void recordStatus(Order order, String status) {
        OrderStatusHistory history = OrderStatusHistory.builder()
                .order(order)
                .status(status)
                .build();
        repository.save(history);
    }

    @Transactional(readOnly = true)
    public List<OrderStatusHistoryResponse> getHistory(Long orderId, OrderRepository orderRepository) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        return repository.findByOrderOrderByChangedAtAsc(order).stream()
                .map(entry -> new OrderStatusHistoryResponse(entry.getId(), entry.getOrder().getId(), entry.getStatus(), entry.getChangedAt()))
                .collect(Collectors.toList());
    }
}
