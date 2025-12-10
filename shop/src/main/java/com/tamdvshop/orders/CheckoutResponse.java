package com.tamdvshop.orders;

import java.math.BigDecimal;

public record CheckoutResponse(
        Long orderId,
        BigDecimal total,
        BigDecimal shippingCost,
        BigDecimal discount
) {
}
