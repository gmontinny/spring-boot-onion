package br.com.onion.infrastructure.repository;

import br.com.onion.domain.entity.OrderPayment;
import br.com.onion.domain.repository.OrderPaymentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaOrderPaymentRepository extends JpaRepository<OrderPayment, Long>, OrderPaymentRepository {
    Page<OrderPayment> findByOrderId(String orderId, Pageable pageable);
}
