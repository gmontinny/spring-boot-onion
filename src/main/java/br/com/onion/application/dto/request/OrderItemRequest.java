package br.com.onion.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Payload para criação de item do pedido")
public class OrderItemRequest {

    @NotBlank(message = "Order ID is required")
    @Size(max = 32)
    @Schema(description = "ID do pedido", example = "e481f51cbdc54678b7cc49136f2d6af7")
    private String orderId;

    @NotNull(message = "Order item ID is required")
    @Positive
    @Schema(description = "Número sequencial do item no pedido", example = "1")
    private Integer orderItemId;

    @NotBlank(message = "Product ID is required")
    @Size(max = 32)
    @Schema(description = "ID do produto", example = "4244733e06e7ecb4970a6e2683c13e61")
    private String productId;

    @NotBlank(message = "Seller ID is required")
    @Size(max = 32)
    @Schema(description = "ID do vendedor", example = "48436dade18ac8b2bce089ec2a041202")
    private String sellerId;

    @NotNull(message = "Price is required")
    @Positive
    @Schema(description = "Preço do item", example = "58.90")
    private BigDecimal price;

    @NotNull(message = "Freight value is required")
    @Schema(description = "Valor do frete", example = "13.29")
    private BigDecimal freightValue;
}
