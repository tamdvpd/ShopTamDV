package com.tamdvshop.vouchers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;

    @Transactional
    public VoucherResponse create(VoucherRequest request) {
        Voucher voucher = new Voucher();
        VoucherMapper.updateEntity(voucher, request);
        return VoucherMapper.toResponse(voucherRepository.save(voucher));
    }

    @Transactional(readOnly = true)
    public List<VoucherResponse> listAll() {
        return voucherRepository.findAll()
                .stream()
                .map(VoucherMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Voucher validateVoucher(String code, BigDecimal orderTotal) {
        Voucher voucher = voucherRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Voucher not found"));
        if (voucher.getExpiredAt() != null && voucher.getExpiredAt().isBefore(Instant.now())) {
            throw new IllegalStateException("Voucher expired");
        }
        if (voucher.getMinOrderPrice() != null && orderTotal.compareTo(voucher.getMinOrderPrice()) < 0) {
            throw new IllegalStateException("Order total does not meet minimum requirement");
        }
        return voucher;
    }
}
