package com.tamdvshop.products.price;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/product-prices")
@RequiredArgsConstructor
public class ProductPriceController {

    private final ProductPriceService productPriceService;

    @PostMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePrice(@PathVariable Long productId, @RequestParam @NotNull @DecimalMin("0.0") BigDecimal newPrice) {
        productPriceService.updatePrice(productId, newPrice);
    }

    @GetMapping("/{productId}/history")
    public List<ProductPriceHistoryResponse> history(@PathVariable Long productId) {
        return productPriceService.getHistory(productId);
    }
}
