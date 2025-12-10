package com.tamdvshop.products;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductVariantRequest(
        @NotNull Long productId,
        @NotBlank String variantName,
        @NotNull @Min(0) Integer stock,
        @NotNull BigDecimal price
) {
}
