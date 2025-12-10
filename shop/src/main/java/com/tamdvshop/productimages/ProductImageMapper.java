package com.tamdvshop.productimages;

public class ProductImageMapper {

    private ProductImageMapper() {
    }

    public static ProductImageResponse toResponse(ProductImage image) {
        return new ProductImageResponse(
                image.getId(),
                image.getProduct().getId(),
                image.getImageUrl()
        );
    }
}
