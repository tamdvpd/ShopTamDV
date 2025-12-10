package com.tamdvshop.payments;

import java.math.BigDecimal;
import java.time.Instant;

public record PaymentTransactionResponse(
        Long id,
        Long orderId,
        String provider,
        BigDecimal amount,
        String status,
        Instant paidAt
) {
}
