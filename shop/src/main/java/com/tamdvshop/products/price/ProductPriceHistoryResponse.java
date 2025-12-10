package com.tamdvshop.products.price;

import java.math.BigDecimal;
import java.time.Instant;

public record ProductPriceHistoryResponse(
        Long id,
        BigDecimal oldPrice,
        BigDecimal newPrice,
        Instant changedAt
) {
}
