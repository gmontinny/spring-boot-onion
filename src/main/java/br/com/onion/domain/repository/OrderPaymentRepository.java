package br.com.onion.domain.repository;

import br.com.onion.domain.entity.OrderPayment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderPaymentRepository {
    Page<OrderPayment> findAll(Pageable pageable);
    Optional<OrderPayment> findById(Long id);
    OrderPayment save(OrderPayment orderPayment);
    void deleteById(Long id);
    Page<OrderPayment> findByOrderId(String orderId, Pageable pageable);
}
