package com.tamdvshop.wishlist;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistService service;

    @GetMapping
    public List<WishlistResponse> list() {
        return service.myWishlist();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public WishlistResponse add(@Valid @RequestBody WishlistRequest request) {
        return service.addToWishlist(request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable Long id) {
        service.remove(id);
    }
}
