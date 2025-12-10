package com.tamdvshop.payments;

import com.tamdvshop.orders.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {
    List<PaymentTransaction> findByOrder(Order order);
}
