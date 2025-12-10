package com.tamdvshop.productimages;

public record ProductImageResponse(
        Long id,
        Long productId,
        String imageUrl
) {
}
