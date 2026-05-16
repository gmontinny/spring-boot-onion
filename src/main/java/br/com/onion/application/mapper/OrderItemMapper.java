package br.com.onion.application.mapper;

import br.com.onion.application.dto.response.OrderItemResponse;
import br.com.onion.domain.entity.OrderItem;

public final class OrderItemMapper {

    private OrderItemMapper() {}

    public static OrderItemResponse toResponse(OrderItem entity) {
        return OrderItemResponse.builder()
                .id(entity.getId())
                .orderId(entity.getOrder().getId())
                .orderItemId(entity.getOrderItemId())
                .productId(entity.getProduct().getId())
                .sellerId(entity.getSeller().getId())
                .shippingLimitDate(entity.getShippingLimitDate())
                .price(entity.getPrice())
                .freightValue(entity.getFreightValue())
                .build();
    }
}
