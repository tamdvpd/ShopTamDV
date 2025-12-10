package com.tamdvshop.shipping;

import java.math.BigDecimal;

public record ShippingMethodResponse(
        Long id,
        String name,
        BigDecimal cost
) {
}
