package br.com.onion.application.mapper;

import br.com.onion.application.dto.request.CustomerRequest;
import br.com.onion.application.dto.response.CustomerResponse;
import br.com.onion.domain.entity.Customer;

import java.util.UUID;

public final class CustomerMapper {

    private CustomerMapper() {}

    public static Customer toEntity(CustomerRequest request) {
        return Customer.builder()
                .id(UUID.randomUUID().toString().replace("-", ""))
                .uniqueId(request.getUniqueId())
                .zipCodePrefix(request.getZipCodePrefix())
                .city(request.getCity())
                .state(request.getState())
                .build();
    }

    public static CustomerResponse toResponse(Customer entity) {
        return CustomerResponse.builder()
                .id(entity.getId())
                .uniqueId(entity.getUniqueId())
                .zipCodePrefix(entity.getZipCodePrefix())
                .city(entity.getCity())
                .state(entity.getState())
                .build();
    }

    public static void updateEntity(Customer entity, CustomerRequest request) {
        entity.setUniqueId(request.getUniqueId());
        entity.setZipCodePrefix(request.getZipCodePrefix());
        entity.setCity(request.getCity());
        entity.setState(request.getState());
    }
}
