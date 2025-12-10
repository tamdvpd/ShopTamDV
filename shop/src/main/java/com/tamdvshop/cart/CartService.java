package com.tamdvshop.cart;

import com.tamdvshop.auth.User;
import com.tamdvshop.auth.UserRepository;
import com.tamdvshop.products.Product;
import com.tamdvshop.products.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public List<CartItem> getCurrentUserCart() {
        User user = getCurrentUser();
        return cartRepository.findByUser(user);
    }

    @Transactional
    public CartItem addToCart(CartRequest request) {
        User user = getCurrentUser();
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        CartItem cartItem = cartRepository.findByUserAndProductId(user, product.getId())
                .orElse(CartItem.builder().user(user).product(product).quantity(0).build());
        cartItem.setQuantity(cartItem.getQuantity() + request.quantity());
        return cartRepository.save(cartItem);
    }

    @Transactional
    public CartItem updateQuantity(Long itemId, int quantity) {
        CartItem cartItem = cartRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Cart item not found"));
        cartItem.setQuantity(quantity);
        return cartRepository.save(cartItem);
    }

    @Transactional
    public void removeItem(Long itemId) {
        cartRepository.deleteById(itemId);
    }

    @Transactional
    public void clearCart() {
        cartRepository.deleteByUser(getCurrentUser());
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("User not found"));
    }
}
