package com.tamdvshop.productimages;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductImageRequest(
        @NotNull Long productId,
        @NotBlank String imageUrl
) {
}
