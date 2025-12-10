package com.tamdvshop.wishlist;

import com.tamdvshop.auth.User;
import com.tamdvshop.common.CurrentUserService;
import com.tamdvshop.products.Product;
import com.tamdvshop.products.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository repository;
    private final ProductRepository productRepository;
    private final CurrentUserService currentUserService;

    @Transactional
    public WishlistResponse addToWishlist(WishlistRequest request) {
        User user = currentUserService.getCurrentUser();
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        repository.findByUserAndProduct(user, product)
                .ifPresent(wishlist -> { throw new IllegalStateException("Product already in wishlist"); });

        Wishlist wishlist = Wishlist.builder()
                .user(user)
                .product(product)
                .build();
        return new WishlistResponse(repository.save(wishlist).getId(), product.getId());
    }

    @Transactional(readOnly = true)
    public List<WishlistResponse> myWishlist() {
        User user = currentUserService.getCurrentUser();
        return repository.findByUser(user)
                .stream()
                .map(item -> new WishlistResponse(item.getId(), item.getProduct().getId()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void remove(Long id) {
        Wishlist wishlist = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Wishlist item not found"));
        if (!wishlist.getUser().equals(currentUserService.getCurrentUser())) {
            throw new IllegalStateException("Cannot remove item of another user");
        }
        repository.delete(wishlist);
    }
}
