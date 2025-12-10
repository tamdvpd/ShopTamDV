package com.tamdvshop.orders;

import com.tamdvshop.auth.User;
import com.tamdvshop.common.CurrentUserService;
import com.tamdvshop.products.Product;
import com.tamdvshop.products.ProductRepository;
import com.tamdvshop.products.ProductVariant;
import com.tamdvshop.products.ProductVariantRepository;
import com.tamdvshop.shipping.ShippingMethod;
import com.tamdvshop.shipping.ShippingMethodService;
import com.tamdvshop.vouchers.Voucher;
import com.tamdvshop.vouchers.VoucherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderProcessingService {

    private final OrderRepository orderRepository;
    private final OrderVoucherRepository orderVoucherRepository;
    private final OrderStatusHistoryService orderStatusHistoryService;
    private final ProductRepository productRepository;
    private final ProductVariantRepository productVariantRepository;
    private final ShippingMethodService shippingMethodService;
    private final VoucherService voucherService;
    private final CurrentUserService currentUserService;

    @Transactional
    public CheckoutResponse checkout(CheckoutRequest request) {
        User user = currentUserService.getCurrentUser();
        Order order = Order.builder()
                .user(user)
                .status(OrderStatus.PENDING)
                .total(BigDecimal.ZERO)
                .build();

        BigDecimal merchandiseTotal = BigDecimal.ZERO;
        for (CheckoutItemRequest itemRequest : request.items()) {
            Product product = productRepository.findById(itemRequest.productId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found"));
            BigDecimal price = product.getPrice();
            if (itemRequest.variantId() != null) {
                ProductVariant variant = productVariantRepository.findById(itemRequest.variantId())
                        .orElseThrow(() -> new IllegalArgumentException("Variant not found"));
                if (!variant.getProduct().equals(product)) {
                    throw new IllegalStateException("Variant does not belong to product");
                }
                if (variant.getStock() < itemRequest.quantity()) {
                    throw new IllegalStateException("Not enough variant stock");
                }
                variant.setStock(variant.getStock() - itemRequest.quantity());
                productVariantRepository.save(variant);
                price = variant.getPrice();
            } else if (product.getStock() != null && product.getStock() < itemRequest.quantity()) {
                throw new IllegalStateException("Not enough product stock");
            } else if (product.getStock() != null) {
                product.setStock(product.getStock() - itemRequest.quantity());
                productRepository.save(product);
            }

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemRequest.quantity())
                    .price(price)
                    .build();
            order.getItems().add(orderItem);
            merchandiseTotal = merchandiseTotal.add(price.multiply(BigDecimal.valueOf(itemRequest.quantity())));
        }

        BigDecimal shippingCost = BigDecimal.ZERO;
        if (request.shippingMethodId() != null) {
            ShippingMethod method = shippingMethodService.getById(request.shippingMethodId());
            shippingCost = method.getCost();
        }

        BigDecimal discount = BigDecimal.ZERO;
        if (request.voucherCode() != null && !request.voucherCode().isBlank()) {
            Voucher voucher = voucherService.validateVoucher(request.voucherCode(), merchandiseTotal);
            discount = merchandiseTotal.multiply(voucher.getDiscountPercent())
                    .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
            if (voucher.getMaxDiscount() != null && discount.compareTo(voucher.getMaxDiscount()) > 0) {
                discount = voucher.getMaxDiscount();
            }
            Order savedOrder = orderRepository.save(order);
            orderVoucherRepository.save(OrderVoucher.builder()
                    .order(savedOrder)
                    .voucher(voucher)
                    .appliedDiscount(discount)
                    .build());
            order = savedOrder;
        }

        BigDecimal total = merchandiseTotal.add(shippingCost).subtract(discount);
        order.setTotal(total);
        Order saved = orderRepository.save(order);
        orderStatusHistoryService.recordStatus(saved, OrderStatus.PENDING.name());
        return new CheckoutResponse(saved.getId(), total, shippingCost, discount);
    }
}
