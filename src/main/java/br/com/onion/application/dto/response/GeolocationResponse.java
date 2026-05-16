package br.com.onion.application.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class GeolocationResponse extends RepresentationModel<GeolocationResponse> {
    private Long id;
    private String zipCodePrefix;
    private BigDecimal lat;
    private BigDecimal lng;
    private String city;
    private String state;
}
