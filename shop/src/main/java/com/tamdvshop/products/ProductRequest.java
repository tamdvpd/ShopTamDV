package com.tamdvshop.products;

import java.math.BigDecimal;

public record ProductRequest(
        String name,
        String description,
        BigDecimal price,
        Integer stock,
        Long categoryId
) { }
