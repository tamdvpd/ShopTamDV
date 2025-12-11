package com.example.shop.service;

import com.example.shop.order.Order;
import com.example.shop.user.User;
import java.util.List;

public interface OrderService {
    List<Order> getOrders(User user);

    Order placeOrder(User user);
}
