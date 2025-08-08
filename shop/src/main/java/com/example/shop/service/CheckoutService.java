package com.example.shop.service;

import com.example.shop.dto.CheckoutRequestDTO;
import com.example.shop.dto.OrderDTO;
import com.example.shop.dto.OrderItemView;
import com.example.shop.entity.*;
import com.example.shop.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CheckoutService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final MailService mailService;

    @Transactional
    public OrderDTO checkoutCOD(Long userId, CheckoutRequestDTO request) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));
        if (cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            if (product.getStock() < item.getQuantity()) {
                throw new IllegalArgumentException("Not enough stock for product " + product.getId());
            }
            BigDecimal line = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            total = total.add(line);
        }
        Order order = new Order();
        order.setUser(cart.getUser());
        order.setTotal(total);
        order.setStatus("CONFIRMED");
        order = orderRepository.save(order);
        for (CartItem item : cart.getItems()) {
            Product product = item.getProduct();
            product.setStock(product.getStock() - item.getQuantity());
            productRepository.save(product);
            OrderItem oi = new OrderItem();
            oi.setOrder(order);
            oi.setProduct(product);
            oi.setQuantity(item.getQuantity());
            oi.setPrice(product.getPrice());
            orderItemRepository.save(oi);
        }
        cartItemRepository.deleteAll(cart.getItems());
        mailService.sendOrderConfirmed(order);
        order = orderRepository.findById(order.getId()).orElseThrow();
        return toDTO(order);
    }

    @Transactional
    public OrderDTO getOrder(Long userId, Long id) {
        Order order = orderRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        return toDTO(order);
    }

    private OrderDTO toDTO(Order order) {
        List<OrderItemView> items = order.getItems().stream()
                .map(oi -> new OrderItemView(oi.getProduct().getId(), oi.getProduct().getName(), oi.getPrice(), oi.getQuantity()))
                .collect(Collectors.toList());
        return new OrderDTO(order.getId(), order.getTotal(), order.getStatus(), order.getCreatedAt(), items);
    }
}

