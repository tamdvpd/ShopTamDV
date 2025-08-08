package com.example.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartViewDTO {
    private List<CartItemView> items;
    private BigDecimal subtotal;
    private BigDecimal total;
}

