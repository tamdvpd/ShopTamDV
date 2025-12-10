package com.tamdvshop.vouchers;

import java.math.BigDecimal;
import java.time.Instant;

public record VoucherResponse(
        Long id,
        String code,
        BigDecimal discountPercent,
        BigDecimal minOrderPrice,
        BigDecimal maxDiscount,
        Instant expiredAt
) {
}
