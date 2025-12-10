package com.tamdvshop.addresses;

public record AddressResponse(
        Long id,
        String fullName,
        String phone,
        String province,
        String district,
        String ward,
        String addressDetail
) {
}
