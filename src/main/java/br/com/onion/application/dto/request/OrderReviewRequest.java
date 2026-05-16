package br.com.onion.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(description = "Payload para criação de avaliação do pedido")
public class OrderReviewRequest {

    @NotBlank(message = "Order ID is required")
    @Size(max = 32)
    @Schema(description = "ID do pedido", example = "73fc7af87114b39712e6da79b0a377eb")
    private String orderId;

    @NotNull(message = "Review score is required")
    @Min(1) @Max(5)
    @Schema(description = "Nota da avaliação (1 a 5)", example = "4")
    private Integer reviewScore;

    @Schema(description = "Título do comentário", example = "Muito bom")
    private String reviewCommentTitle;

    @Schema(description = "Mensagem do comentário", example = "Produto chegou antes do prazo")
    private String reviewCommentMessage;
}
