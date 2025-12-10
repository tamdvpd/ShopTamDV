package com.tamdvshop.shipping;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ShippingMethodRequest(
        @NotBlank String name,
        @NotNull @DecimalMin("0.0") BigDecimal cost
) {
}
