package com.example.shop.controller;

import com.example.shop.dto.CheckoutRequestDTO;
import com.example.shop.dto.OrderDTO;
import com.example.shop.service.CheckoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final CheckoutService checkoutService;

    @PostMapping("/checkout")
    public OrderDTO checkout(@RequestHeader("X-USER-ID") Long userId,
                             @RequestBody @Valid CheckoutRequestDTO request) {
        return checkoutService.checkoutCOD(userId, request);
    }

    @GetMapping("/{id}")
    public OrderDTO getOrder(@RequestHeader("X-USER-ID") Long userId,
                             @PathVariable Long id) {
        return checkoutService.getOrder(userId, id);
    }
}

