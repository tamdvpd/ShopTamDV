package com.example.shop.service.impl;

import com.example.shop.cart.CartItem;
import com.example.shop.cart.CartRequest;
import com.example.shop.product.Product;
import com.example.shop.repository.CartItemRepository;
import com.example.shop.repository.ProductRepository;
import com.example.shop.service.CartService;
import com.example.shop.user.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Override
    public List<CartItem> getCart(User user) {
        return cartItemRepository.findByUser(user);
    }

    @Override
    @Transactional
    public CartItem addToCart(User user, CartRequest request) {
        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        CartItem item = cartItemRepository.findByUserIdAndProductId(user.getId(), product.getId())
                .orElseGet(() -> {
                    CartItem ci = new CartItem();
                    ci.setUser(user);
                    ci.setProduct(product);
                    ci.setQuantity(0);
                    return ci;
                });
        item.setQuantity(item.getQuantity() + request.getQuantity());
        return cartItemRepository.save(item);
    }

    @Override
    @Transactional
    public CartItem updateQuantity(User user, CartRequest request) {
        CartItem item = cartItemRepository.findByUserIdAndProductId(user.getId(), request.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Item not found in cart"));
        item.setQuantity(request.getQuantity());
        return cartItemRepository.save(item);
    }

    @Override
    @Transactional
    public void removeFromCart(User user, Long productId) {
        cartItemRepository.findByUserIdAndProductId(user.getId(), productId)
                .ifPresent(cartItemRepository::delete);
    }

    @Override
    @Transactional
    public void clear(User user) {
        getCart(user).forEach(cartItemRepository::delete);
    }
}
