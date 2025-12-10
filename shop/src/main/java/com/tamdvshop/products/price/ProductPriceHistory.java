package com.tamdvshop.products.price;

import com.tamdvshop.products.Product;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "product_price_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductPriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private BigDecimal oldPrice;

    @Column(nullable = false)
    private BigDecimal newPrice;

    @Column(nullable = false, updatable = false)
    private Instant changedAt;

    @PrePersist
    public void onCreate() {
        changedAt = Instant.now();
    }
}
