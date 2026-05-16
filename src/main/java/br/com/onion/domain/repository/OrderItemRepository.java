package br.com.onion.domain.repository;

import br.com.onion.domain.entity.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderItemRepository {
    Page<OrderItem> findAll(Pageable pageable);
    Optional<OrderItem> findById(Long id);
    OrderItem save(OrderItem orderItem);
    void deleteById(Long id);
    Page<OrderItem> findByOrderId(String orderId, Pageable pageable);
}
