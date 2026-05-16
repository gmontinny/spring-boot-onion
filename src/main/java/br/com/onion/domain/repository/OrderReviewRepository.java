package br.com.onion.domain.repository;

import br.com.onion.domain.entity.OrderReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface OrderReviewRepository {
    Page<OrderReview> findAll(Pageable pageable);
    Optional<OrderReview> findById(String id);
    OrderReview save(OrderReview orderReview);
    void deleteById(String id);
    Page<OrderReview> findByOrderId(String orderId, Pageable pageable);
}
