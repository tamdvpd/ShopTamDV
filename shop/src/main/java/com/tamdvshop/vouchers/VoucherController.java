package com.tamdvshop.vouchers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vouchers")
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VoucherResponse create(@Valid @RequestBody VoucherRequest request) {
        return voucherService.create(request);
    }

    @GetMapping
    public List<VoucherResponse> list() {
        return voucherService.listAll();
    }
}
