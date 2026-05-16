package br.com.onion.application.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class OrderItemResponse extends RepresentationModel<OrderItemResponse> {
    private Long id;
    private String orderId;
    private Integer orderItemId;
    private String productId;
    private String sellerId;
    private LocalDateTime shippingLimitDate;
    private BigDecimal price;
    private BigDecimal freightValue;
}
