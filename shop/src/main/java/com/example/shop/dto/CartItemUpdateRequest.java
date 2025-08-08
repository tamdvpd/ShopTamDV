package com.example.shop.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class CartItemUpdateRequest {
    @Min(1)
    private int quantity;
}

