package com.example.shop.admin;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AdminStats {
    private long users;
    private long orders;
    private BigDecimal revenue;
}
