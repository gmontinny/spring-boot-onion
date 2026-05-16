package br.com.onion.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @Column(name = "order_id", length = 32)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "order_status", length = 20)
    private String status;

    @Column(name = "order_purchase_timestamp")
    private LocalDateTime purchaseTimestamp;

    @Column(name = "order_approved_at")
    private LocalDateTime approvedAt;

    @Column(name = "order_delivered_carrier_date")
    private LocalDateTime deliveredCarrierDate;

    @Column(name = "order_delivered_customer_date")
    private LocalDateTime deliveredCustomerDate;

    @Column(name = "order_estimated_delivery_date")
    private LocalDateTime estimatedDeliveryDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> items;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderPayment> payments;
}
