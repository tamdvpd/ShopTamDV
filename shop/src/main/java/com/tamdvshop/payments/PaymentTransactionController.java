package com.tamdvshop.payments;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentTransactionController {

    private final PaymentTransactionService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentTransactionResponse create(@Valid @RequestBody PaymentTransactionRequest request) {
        return service.create(request);
    }

    @GetMapping("/order/{orderId}")
    public List<PaymentTransactionResponse> listByOrder(@PathVariable Long orderId) {
        return service.findByOrder(orderId);
    }
}
