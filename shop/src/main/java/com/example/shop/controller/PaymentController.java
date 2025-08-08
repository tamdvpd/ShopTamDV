package com.example.shop.controller;

import com.example.shop.dto.PaymentCreateRequest;
import com.example.shop.entity.Order;
import com.example.shop.entity.Payment;
import com.example.shop.repository.OrderRepository;
import com.example.shop.repository.PaymentRepository;
import com.example.shop.service.MailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final MailService mailService;

    @PostMapping("/create")
    public Map<String, String> create(@RequestBody @Valid PaymentCreateRequest request) {
        Order order = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        Payment payment = paymentRepository.findByOrderId(order.getId()).orElse(new Payment());
        payment.setOrder(order);
        payment.setAmount(order.getTotal());
        payment.setMethod(request.getMethod());
        payment.setStatus("PENDING");
        paymentRepository.save(payment);
        Map<String, String> res = new HashMap<>();
        res.put("callbackUrl", "/payments/callback?orderId=" + order.getId() + "&status=success");
        return res;
    }

    @GetMapping("/callback")
    public Map<String, String> callback(@RequestParam Long orderId, @RequestParam String status) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"));
        if ("success".equalsIgnoreCase(status)) {
            payment.setStatus("SUCCESS");
            paymentRepository.save(payment);
            order.setStatus("PAID");
            orderRepository.save(order);
            mailService.sendOrderPaid(order);
        }
        Map<String, String> res = new HashMap<>();
        res.put("status", "ok");
        return res;
    }
}

