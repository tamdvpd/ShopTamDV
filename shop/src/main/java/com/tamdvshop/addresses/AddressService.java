package com.tamdvshop.addresses;

import com.tamdvshop.common.CurrentUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final CurrentUserService currentUserService;

    @Transactional(readOnly = true)
    public List<AddressResponse> getMyAddresses() {
        return addressRepository.findByUser(currentUserService.getCurrentUser())
                .stream()
                .map(AddressMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public AddressResponse create(AddressRequest request) {
        Address address = Address.builder()
                .user(currentUserService.getCurrentUser())
                .build();
        AddressMapper.updateEntity(address, request);
        return AddressMapper.toResponse(addressRepository.save(address));
    }

    @Transactional
    public AddressResponse update(Long id, AddressRequest request) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));
        if (!address.getUser().equals(currentUserService.getCurrentUser())) {
            throw new IllegalStateException("Cannot update another user's address");
        }
        AddressMapper.updateEntity(address, request);
        return AddressMapper.toResponse(addressRepository.save(address));
    }

    @Transactional
    public void delete(Long id) {
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Address not found"));
        if (!address.getUser().equals(currentUserService.getCurrentUser())) {
            throw new IllegalStateException("Cannot delete another user's address");
        }
        addressRepository.delete(address);
    }
}
