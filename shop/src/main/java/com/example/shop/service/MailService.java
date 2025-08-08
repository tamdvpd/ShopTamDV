package com.example.shop.service;

import com.example.shop.entity.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MailService {
    public void sendOrderConfirmed(Order order) {
        log.info("[MAIL] Order {} confirmed", order.getId());
    }

    public void sendOrderPaid(Order order) {
        log.info("[MAIL] Order {} paid", order.getId());
    }
}

