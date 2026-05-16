package br.com.onion.application.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class CustomerResponse extends RepresentationModel<CustomerResponse> {
    private String id;
    private String uniqueId;
    private String zipCodePrefix;
    private String city;
    private String state;
}
