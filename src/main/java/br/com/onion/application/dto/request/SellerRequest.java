package br.com.onion.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Payload para criação/atualização de vendedor")
public class SellerRequest {

    @NotBlank(message = "Zip code prefix is required")
    @Size(max = 5)
    @Schema(description = "Prefixo do CEP do vendedor", example = "13023")
    private String zipCodePrefix;

    @NotBlank(message = "City is required")
    @Size(max = 100)
    @Schema(description = "Cidade do vendedor", example = "campinas")
    private String city;

    @NotBlank(message = "State is required")
    @Size(max = 2)
    @Schema(description = "Estado (UF)", example = "SP")
    private String state;
}
