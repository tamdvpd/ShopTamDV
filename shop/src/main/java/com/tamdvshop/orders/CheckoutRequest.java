package com.tamdvshop.orders;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CheckoutRequest(
        @Valid @NotEmpty List<CheckoutItemRequest> items,
        Long shippingMethodId,
        String voucherCode
) {
}
