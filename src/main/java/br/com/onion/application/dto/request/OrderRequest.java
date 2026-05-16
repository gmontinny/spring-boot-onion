package br.com.onion.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Payload para criação de pedido")
public class OrderRequest {

    @NotBlank(message = "Customer ID is required")
    @Size(max = 32)
    @Schema(description = "ID do cliente associado ao pedido", example = "9ef432eb6251297304e76186b10a928d")
    private String customerId;

    @NotBlank(message = "Order status is required")
    @Size(max = 20)
    @Schema(description = "Status do pedido", example = "created")
    private String status;
}
