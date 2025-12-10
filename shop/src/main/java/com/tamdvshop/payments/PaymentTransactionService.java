package com.tamdvshop.payments;

import com.tamdvshop.orders.Order;
import com.tamdvshop.orders.OrderRepository;
import com.tamdvshop.orders.OrderStatus;
import com.tamdvshop.orders.OrderStatusHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentTransactionService {

    private final PaymentTransactionRepository repository;
    private final OrderRepository orderRepository;
    private final OrderStatusHistoryService orderStatusHistoryService;

    @Transactional
    public PaymentTransactionResponse create(PaymentTransactionRequest request) {
        Order order = orderRepository.findById(request.orderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        PaymentTransaction transaction = PaymentTransaction.builder()
                .order(order)
                .provider(request.provider())
                .amount(request.amount())
                .status(request.status())
                .paidAt(Instant.now())
                .build();
        PaymentTransaction saved = repository.save(transaction);
        if ("SUCCESS".equalsIgnoreCase(request.status())) {
            order.setStatus(OrderStatus.PAID);
            orderRepository.save(order);
            orderStatusHistoryService.recordStatus(order, OrderStatus.PAID.name());
        }
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<PaymentTransactionResponse> findByOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        return repository.findByOrder(order)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    private PaymentTransactionResponse toResponse(PaymentTransaction transaction) {
        return new PaymentTransactionResponse(
                transaction.getId(),
                transaction.getOrder().getId(),
                transaction.getProvider(),
                transaction.getAmount(),
                transaction.getStatus(),
                transaction.getPaidAt()
        );
    }
}
