package br.com.onion.application.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class OrderResponse extends RepresentationModel<OrderResponse> {
    private String id;
    private String customerId;
    private String status;
    private LocalDateTime purchaseTimestamp;
    private LocalDateTime approvedAt;
    private LocalDateTime deliveredCarrierDate;
    private LocalDateTime deliveredCustomerDate;
    private LocalDateTime estimatedDeliveryDate;
}
