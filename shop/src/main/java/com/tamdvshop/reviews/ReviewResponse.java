package com.tamdvshop.reviews;

import java.time.Instant;

public record ReviewResponse(
        Long id,
        Long productId,
        int rating,
        String content,
        Instant createdAt,
        Double averageRating
) {
}
