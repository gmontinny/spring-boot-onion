package br.com.onion.application.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class OrderPaymentResponse extends RepresentationModel<OrderPaymentResponse> {
    private Long id;
    private String orderId;
    private Integer paymentSequential;
    private String paymentType;
    private Integer paymentInstallments;
    private BigDecimal paymentValue;
}
