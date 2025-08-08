package com.example.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemView {
    private Long itemId;
    private Long productId;
    private String name;
    private BigDecimal price;
    private int quantity;
}

