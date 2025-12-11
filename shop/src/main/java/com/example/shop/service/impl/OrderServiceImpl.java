package com.example.shop.service.impl;

import com.example.shop.cart.CartItem;
import com.example.shop.order.Order;
import com.example.shop.order.OrderItem;
import com.example.shop.repository.CartItemRepository;
import com.example.shop.repository.OrderRepository;
import com.example.shop.service.OrderService;
import com.example.shop.user.User;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CartItemRepository cartItemRepository;

    @Override
    public List<Order> getOrders(User user) {
        return orderRepository.findByUser(user);
    }

    @Override
    @Transactional
    public Order placeOrder(User user) {
        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        if (cartItems.isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }
        Order order = new Order();
        order.setUser(user);
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem item : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setPrice(item.getProduct().getPrice());
            order.getItems().add(orderItem);
            total = total.add(item.getProduct().getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        order.setTotal(total);
        Order saved = orderRepository.save(order);
        cartItems.forEach(cartItemRepository::delete);
        return saved;
    }
}
