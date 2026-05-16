package br.com.onion.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Payload para criação/atualização de produto")
public class ProductRequest {

    @NotBlank(message = "Category name is required")
    @Size(max = 100)
    @Schema(description = "Nome da categoria do produto", example = "perfumaria")
    private String categoryName;

    @Positive
    @Schema(description = "Comprimento do nome do produto", example = "40")
    private Integer nameLength;

    @Positive
    @Schema(description = "Comprimento da descrição do produto", example = "287")
    private Integer descriptionLength;

    @Positive
    @Schema(description = "Quantidade de fotos", example = "1")
    private Integer photosQty;

    @Positive
    @Schema(description = "Peso em gramas", example = "225")
    private Integer weightG;

    @Positive
    @Schema(description = "Comprimento em cm", example = "16")
    private Integer lengthCm;

    @Positive
    @Schema(description = "Altura em cm", example = "10")
    private Integer heightCm;

    @Positive
    @Schema(description = "Largura em cm", example = "14")
    private Integer widthCm;
}
