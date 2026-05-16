package br.com.onion.infrastructure.repository;

import br.com.onion.domain.entity.Order;
import br.com.onion.domain.repository.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaOrderRepository extends JpaRepository<Order, String>, OrderRepository {
    Page<Order> findByCustomerId(String customerId, Pageable pageable);
}
