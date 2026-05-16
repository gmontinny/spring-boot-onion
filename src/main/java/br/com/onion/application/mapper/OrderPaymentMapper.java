package br.com.onion.application.mapper;

import br.com.onion.application.dto.response.OrderPaymentResponse;
import br.com.onion.domain.entity.OrderPayment;

public final class OrderPaymentMapper {

    private OrderPaymentMapper() {}

    public static OrderPaymentResponse toResponse(OrderPayment entity) {
        return OrderPaymentResponse.builder()
                .id(entity.getId())
                .orderId(entity.getOrder().getId())
                .paymentSequential(entity.getPaymentSequential())
                .paymentType(entity.getPaymentType())
                .paymentInstallments(entity.getPaymentInstallments())
                .paymentValue(entity.getPaymentValue())
                .build();
    }
}
