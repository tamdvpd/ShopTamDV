package com.tamdvshop.vouchers;

public class VoucherMapper {
    private VoucherMapper() {}

    public static VoucherResponse toResponse(Voucher voucher) {
        return new VoucherResponse(
                voucher.getId(),
                voucher.getCode(),
                voucher.getDiscountPercent(),
                voucher.getMinOrderPrice(),
                voucher.getMaxDiscount(),
                voucher.getExpiredAt()
        );
    }

    public static void updateEntity(Voucher voucher, VoucherRequest request) {
        voucher.setCode(request.code());
        voucher.setDiscountPercent(request.discountPercent());
        voucher.setMinOrderPrice(request.minOrderPrice());
        voucher.setMaxDiscount(request.maxDiscount());
        voucher.setExpiredAt(request.expiredAt());
    }
}
