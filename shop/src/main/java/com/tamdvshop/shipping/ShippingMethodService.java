package com.tamdvshop.shipping;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShippingMethodService {

    private final ShippingMethodRepository repository;

    @Transactional
    public ShippingMethodResponse create(ShippingMethodRequest request) {
        ShippingMethod method = ShippingMethod.builder()
                .name(request.name())
                .cost(request.cost())
                .build();
        return toResponse(repository.save(method));
    }

    @Transactional(readOnly = true)
    public List<ShippingMethodResponse> list() {
        return repository.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ShippingMethod getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Shipping method not found"));
    }

    private ShippingMethodResponse toResponse(ShippingMethod method) {
        return new ShippingMethodResponse(method.getId(), method.getName(), method.getCost());
    }
}
