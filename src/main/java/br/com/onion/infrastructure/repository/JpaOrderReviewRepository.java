package br.com.onion.infrastructure.repository;

import br.com.onion.domain.entity.OrderReview;
import br.com.onion.domain.repository.OrderReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaOrderReviewRepository extends JpaRepository<OrderReview, String>, OrderReviewRepository {
    Page<OrderReview> findByOrderId(String orderId, Pageable pageable);
}
