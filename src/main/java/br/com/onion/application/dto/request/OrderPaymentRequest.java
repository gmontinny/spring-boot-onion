package br.com.onion.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Payload para criação de pagamento do pedido")
public class OrderPaymentRequest {

    @NotBlank(message = "Order ID is required")
    @Size(max = 32)
    @Schema(description = "ID do pedido", example = "b81ef226f3fe1789b1e8b2acac839d17")
    private String orderId;

    @NotNull(message = "Payment sequential is required")
    @Positive
    @Schema(description = "Número sequencial do pagamento", example = "1")
    private Integer paymentSequential;

    @NotBlank(message = "Payment type is required")
    @Size(max = 20)
    @Schema(description = "Tipo de pagamento", example = "credit_card")
    private String paymentType;

    @NotNull(message = "Payment installments is required")
    @Positive
    @Schema(description = "Número de parcelas", example = "8")
    private Integer paymentInstallments;

    @NotNull(message = "Payment value is required")
    @Positive
    @Schema(description = "Valor do pagamento", example = "99.33")
    private BigDecimal paymentValue;
}
