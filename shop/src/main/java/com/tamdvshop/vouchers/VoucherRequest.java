package com.tamdvshop.vouchers;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.Instant;

public record VoucherRequest(
        @NotBlank String code,
        @NotNull @DecimalMin("0.0") BigDecimal discountPercent,
        @DecimalMin("0.0") BigDecimal minOrderPrice,
        @DecimalMin("0.0") BigDecimal maxDiscount,
        Instant expiredAt
) {
}
