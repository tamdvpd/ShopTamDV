package com.tamdvshop.orders;

import java.time.Instant;

public record OrderStatusHistoryResponse(
        Long id,
        Long orderId,
        String status,
        Instant changedAt
) {
}
