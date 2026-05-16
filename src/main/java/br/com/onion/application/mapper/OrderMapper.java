package br.com.onion.application.mapper;

import br.com.onion.application.dto.response.OrderResponse;
import br.com.onion.domain.entity.Order;

public final class OrderMapper {

    private OrderMapper() {}

    public static OrderResponse toResponse(Order entity) {
        return OrderResponse.builder()
                .id(entity.getId())
                .customerId(entity.getCustomer().getId())
                .status(entity.getStatus())
                .purchaseTimestamp(entity.getPurchaseTimestamp())
                .approvedAt(entity.getApprovedAt())
                .deliveredCarrierDate(entity.getDeliveredCarrierDate())
                .deliveredCustomerDate(entity.getDeliveredCustomerDate())
                .estimatedDeliveryDate(entity.getEstimatedDeliveryDate())
                .build();
    }
}
