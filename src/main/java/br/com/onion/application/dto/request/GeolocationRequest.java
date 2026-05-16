package br.com.onion.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Payload para criação/atualização de geolocalização")
public class GeolocationRequest {

    @NotBlank(message = "Zip code prefix is required")
    @Size(max = 5)
    @Schema(description = "Prefixo do CEP", example = "01037")
    private String zipCodePrefix;

    @NotNull(message = "Latitude is required")
    @Schema(description = "Latitude", example = "-23.545621")
    private BigDecimal lat;

    @NotNull(message = "Longitude is required")
    @Schema(description = "Longitude", example = "-46.639292")
    private BigDecimal lng;

    @NotBlank(message = "City is required")
    @Size(max = 100)
    @Schema(description = "Cidade", example = "sao paulo")
    private String city;

    @NotBlank(message = "State is required")
    @Size(max = 2)
    @Schema(description = "Estado (UF)", example = "SP")
    private String state;
}
