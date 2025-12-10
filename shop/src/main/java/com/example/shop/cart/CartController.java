package com.example.shop.cart;

import com.example.shop.common.ApiResponse;
import com.example.shop.user.User;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<CartItem>>> getCart(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ApiResponse.success(cartService.getCart(user)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CartItem>> addToCart(@AuthenticationPrincipal User user,
            @Valid @RequestBody CartRequest request) {
        return ResponseEntity.ok(ApiResponse.success(cartService.addToCart(user, request)));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<CartItem>> updateQuantity(@AuthenticationPrincipal User user,
            @Valid @RequestBody CartRequest request) {
        return ResponseEntity.ok(ApiResponse.success(cartService.updateQuantity(user, request)));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponse<Void>> remove(@AuthenticationPrincipal User user, @PathVariable Long productId) {
        cartService.removeFromCart(user, productId);
        return ResponseEntity.ok(ApiResponse.success("Removed", null));
    }
}
