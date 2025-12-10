package com.tamdvshop.shipping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipping-methods")
@RequiredArgsConstructor
public class ShippingMethodController {

    private final ShippingMethodService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShippingMethodResponse create(@Valid @RequestBody ShippingMethodRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<ShippingMethodResponse> list() {
        return service.list();
    }
}
