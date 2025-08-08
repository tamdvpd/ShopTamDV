package com.example.shop.service;

import com.example.shop.dto.CartItemView;
import com.example.shop.dto.CartViewDTO;
import com.example.shop.entity.Cart;
import com.example.shop.entity.CartItem;
import com.example.shop.entity.Product;
import com.example.shop.entity.User;
import com.example.shop.repository.CartItemRepository;
import com.example.shop.repository.CartRepository;
import com.example.shop.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    @Transactional
    public CartViewDTO addItem(Long userId, Long productId, int quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("Not enough stock");
        }
        Cart cart = cartRepository.findByUserId(userId).orElseGet(() -> {
            Cart c = new Cart();
            User user = new User();
            user.setId(userId);
            c.setUser(user);
            return cartRepository.save(c);
        });
        CartItem item = cartItemRepository.findByCartIdAndProductId(cart.getId(), productId).orElse(null);
        if (item == null) {
            item = new CartItem();
            item.setCart(cart);
            item.setProduct(product);
            item.setQuantity(quantity);
        } else {
            int newQty = item.getQuantity() + quantity;
            if (product.getStock() < newQty) {
                throw new IllegalArgumentException("Not enough stock");
            }
            item.setQuantity(newQty);
        }
        cartItemRepository.save(item);
        cart = cartRepository.findById(cart.getId()).orElseThrow();
        return toView(cart);
    }

    @Transactional
    public CartViewDTO updateItem(Long userId, Long itemId, int quantity) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));
        if (!item.getCart().getId().equals(cart.getId())) {
            throw new IllegalArgumentException("Item not in cart");
        }
        Product product = item.getProduct();
        if (product.getStock() < quantity) {
            throw new IllegalArgumentException("Not enough stock");
        }
        item.setQuantity(quantity);
        cartItemRepository.save(item);
        cart = cartRepository.findById(cart.getId()).orElseThrow();
        return toView(cart);
    }

    @Transactional
    public CartViewDTO removeItem(Long userId, Long itemId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Cart not found"));
        CartItem item = cartItemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found"));
        if (!item.getCart().getId().equals(cart.getId())) {
            throw new IllegalArgumentException("Item not in cart");
        }
        cartItemRepository.delete(item);
        cart = cartRepository.findById(cart.getId()).orElseThrow();
        return toView(cart);
    }

    @Transactional
    public CartViewDTO getCart(Long userId) {
        Cart cart = cartRepository.findByUserId(userId).orElse(null);
        if (cart == null) {
            return new CartViewDTO(new ArrayList<>(), BigDecimal.ZERO, BigDecimal.ZERO);
        }
        return toView(cart);
    }

    private CartViewDTO toView(Cart cart) {
        List<CartItemView> items = cart.getItems().stream()
                .map(ci -> new CartItemView(ci.getId(), ci.getProduct().getId(), ci.getProduct().getName(), ci.getProduct().getPrice(), ci.getQuantity()))
                .collect(Collectors.toList());
        BigDecimal subtotal = items.stream()
                .map(i -> i.getPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        return new CartViewDTO(items, subtotal, subtotal);
    }
}

