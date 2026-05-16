package br.com.onion.domain.repository;

import br.com.onion.domain.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderRepository {
    Page<Order> findAll(Pageable pageable);
    Optional<Order> findById(String id);
    Order save(Order order);
    void deleteById(String id);
    Page<Order> findByCustomerId(String customerId, Pageable pageable);
}
