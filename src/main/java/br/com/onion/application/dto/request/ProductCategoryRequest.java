package br.com.onion.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Payload para criação/atualização de tradução de categoria")
public class ProductCategoryRequest {

    @NotBlank(message = "Category name is required")
    @Size(max = 100)
    @Schema(description = "Nome da categoria em português", example = "beleza_saude")
    private String categoryName;

    @NotBlank(message = "Category name in English is required")
    @Size(max = 100)
    @Schema(description = "Nome da categoria em inglês", example = "health_beauty")
    private String categoryNameEnglish;
}
