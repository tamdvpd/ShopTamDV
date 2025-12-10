package com.tamdvshop.products;

public class ProductVariantMapper {
    private ProductVariantMapper() {
    }

    public static ProductVariantResponse toResponse(ProductVariant variant) {
        return new ProductVariantResponse(
                variant.getId(),
                variant.getProduct().getId(),
                variant.getVariantName(),
                variant.getStock(),
                variant.getPrice()
        );
    }
}
