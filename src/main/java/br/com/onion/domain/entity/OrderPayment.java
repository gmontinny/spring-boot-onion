package br.com.onion.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "order_payments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "payment_sequential", nullable = false)
    private Integer paymentSequential;

    @Column(name = "payment_type", length = 20)
    private String paymentType;

    @Column(name = "payment_installments")
    private Integer paymentInstallments;

    @Column(name = "payment_value", precision = 10, scale = 2)
    private BigDecimal paymentValue;
}
