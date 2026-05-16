package br.com.onion.infrastructure.repository;

import br.com.onion.domain.entity.OrderItem;
import br.com.onion.domain.repository.OrderItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaOrderItemRepository extends JpaRepository<OrderItem, Long>, OrderItemRepository {
    Page<OrderItem> findByOrderId(String orderId, Pageable pageable);
}
