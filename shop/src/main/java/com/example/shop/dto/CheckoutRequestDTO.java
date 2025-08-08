package com.example.shop.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CheckoutRequestDTO {
    @NotBlank
    private String fullName;
    @NotBlank
    private String phone;
    @NotBlank
    private String address;
}

