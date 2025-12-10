package com.tamdvshop.addresses;

public class AddressMapper {

    private AddressMapper() {
    }

    public static AddressResponse toResponse(Address address) {
        return new AddressResponse(
                address.getId(),
                address.getFullName(),
                address.getPhone(),
                address.getProvince(),
                address.getDistrict(),
                address.getWard(),
                address.getAddressDetail()
        );
    }

    public static void updateEntity(Address address, AddressRequest request) {
        address.setFullName(request.fullName());
        address.setPhone(request.phone());
        address.setProvince(request.province());
        address.setDistrict(request.district());
        address.setWard(request.ward());
        address.setAddressDetail(request.addressDetail());
    }
}
