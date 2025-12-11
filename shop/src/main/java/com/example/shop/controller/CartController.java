package com.example.shop.controller;

import com.example.shop.cart.CartItem;
import com.example.shop.cart.CartRequest;
import com.example.shop.service.CartService;
import com.example.shop.user.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<List<CartItem>> getCart(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(cartService.getCart(user));
    }

    @PostMapping
    public ResponseEntity<CartItem> addToCart(@AuthenticationPrincipal User user, @RequestBody CartRequest request) {
        return ResponseEntity.ok(cartService.addToCart(user, request));
    }

    @PutMapping
    public ResponseEntity<CartItem> updateQuantity(@AuthenticationPrincipal User user, @RequestBody CartRequest request) {
        return ResponseEntity.ok(cartService.updateQuantity(user, request));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeFromCart(@AuthenticationPrincipal User user, @PathVariable Long productId) {
        cartService.removeFromCart(user, productId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Void> clear(@AuthenticationPrincipal User user) {
        cartService.clear(user);
        return ResponseEntity.noContent().build();
    }
}
