package com.tamdvshop.reviews;

import com.tamdvshop.auth.User;
import com.tamdvshop.common.CurrentUserService;
import com.tamdvshop.orders.OrderRepository;
import com.tamdvshop.products.Product;
import com.tamdvshop.products.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final CurrentUserService currentUserService;

    @Transactional
    public ReviewResponse create(ReviewRequest request) {
        User user = currentUserService.getCurrentUser();
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        boolean hasPurchased = orderRepository.findByUser(user).stream()
                .flatMap(order -> order.getItems().stream())
                .anyMatch(item -> item.getProduct().equals(product));
        if (!hasPurchased) {
            throw new IllegalStateException("User must purchase product before reviewing");
        }

        reviewRepository.findByUserAndProduct(user, product)
                .ifPresent(existing -> { throw new IllegalStateException("Review already exists"); });

        Review review = Review.builder()
                .user(user)
                .product(product)
                .rating(request.rating())
                .content(request.content())
                .build();
        Review saved = reviewRepository.save(review);
        Double average = reviewRepository.findAverageRatingByProduct(product);
        return new ReviewResponse(saved.getId(), product.getId(), saved.getRating(), saved.getContent(), saved.getCreatedAt(), average);
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> listByProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        Double average = reviewRepository.findAverageRatingByProduct(product);
        return reviewRepository.findByProduct(product).stream()
                .map(review -> new ReviewResponse(review.getId(), product.getId(), review.getRating(), review.getContent(), review.getCreatedAt(), average))
                .collect(Collectors.toList());
    }
}
