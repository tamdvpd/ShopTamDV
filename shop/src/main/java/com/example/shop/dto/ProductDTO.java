package com.example.shop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductDTO {
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String slug;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Integer stock;

    private String imageUrl;

    @NotNull
    private Long categoryId;
}
