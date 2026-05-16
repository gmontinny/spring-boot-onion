package br.com.onion.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Payload para criação/atualização de cliente")
public class CustomerRequest {

    @NotBlank(message = "Customer unique ID is required")
    @Size(max = 32)
    @Schema(description = "Identificador único do cliente", example = "861eff4711a542e4b93843c6dd7febb0")
    private String uniqueId;

    @NotBlank(message = "Zip code prefix is required")
    @Size(max = 5)
    @Schema(description = "Prefixo do CEP", example = "01001")
    private String zipCodePrefix;

    @NotBlank(message = "City is required")
    @Size(max = 100)
    @Schema(description = "Cidade do cliente", example = "São Paulo")
    private String city;

    @NotBlank(message = "State is required")
    @Size(max = 2)
    @Schema(description = "Estado (UF)", example = "SP")
    private String state;
}
