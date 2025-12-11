package com.example.shop.service;

import com.example.shop.cart.CartItem;
import com.example.shop.cart.CartRequest;
import com.example.shop.user.User;
import java.util.List;

public interface CartService {
    List<CartItem> getCart(User user);

    CartItem addToCart(User user, CartRequest request);

    CartItem updateQuantity(User user, CartRequest request);

    void removeFromCart(User user, Long productId);

    void clear(User user);
}
