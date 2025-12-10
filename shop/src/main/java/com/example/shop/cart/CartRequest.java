package com.example.shop.cart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CartRequest {
    @NotNull
    private Long productId;

    @NotNull
    @Positive
    private Integer quantity;
}
