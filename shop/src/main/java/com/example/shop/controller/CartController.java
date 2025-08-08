package com.example.shop.controller;

import com.example.shop.dto.CartItemRequest;
import com.example.shop.dto.CartItemUpdateRequest;
import com.example.shop.dto.CartViewDTO;
import com.example.shop.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PostMapping("/items")
    public CartViewDTO addItem(@RequestHeader("X-USER-ID") Long userId,
                               @RequestBody @Valid CartItemRequest request) {
        return cartService.addItem(userId, request.getProductId(), request.getQuantity());
    }

    @PutMapping("/items/{itemId}")
    public CartViewDTO updateItem(@RequestHeader("X-USER-ID") Long userId,
                                  @PathVariable Long itemId,
                                  @RequestBody @Valid CartItemUpdateRequest request) {
        return cartService.updateItem(userId, itemId, request.getQuantity());
    }

    @DeleteMapping("/items/{itemId}")
    public CartViewDTO deleteItem(@RequestHeader("X-USER-ID") Long userId,
                                  @PathVariable Long itemId) {
        return cartService.removeItem(userId, itemId);
    }

    @GetMapping
    public CartViewDTO getCart(@RequestHeader("X-USER-ID") Long userId) {
        return cartService.getCart(userId);
    }
}

