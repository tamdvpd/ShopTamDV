package com.tamdvshop.products;

import java.math.BigDecimal;

public record ProductVariantResponse(
        Long id,
        Long productId,
        String variantName,
        Integer stock,
        BigDecimal price
) {
}
