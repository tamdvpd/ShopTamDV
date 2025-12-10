package com.tamdvshop.addresses;

import jakarta.validation.constraints.NotBlank;

public record AddressRequest(
        @NotBlank String fullName,
        @NotBlank String phone,
        @NotBlank String province,
        @NotBlank String district,
        @NotBlank String ward,
        @NotBlank String addressDetail
) {
}
