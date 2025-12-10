package com.tamdvshop.orders;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CheckoutItemRequest(
        @NotNull Long productId,
        Long variantId,
        @NotNull @Min(1) Integer quantity
) {
}
