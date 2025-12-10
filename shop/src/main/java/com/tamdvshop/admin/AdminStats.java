package com.tamdvshop.admin;

import java.math.BigDecimal;

public record AdminStats(
        long totalOrders,
        BigDecimal totalRevenue,
        long totalProducts,
        long totalUsers
) { }
