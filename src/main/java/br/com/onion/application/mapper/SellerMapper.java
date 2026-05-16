package br.com.onion.application.mapper;

import br.com.onion.application.dto.request.SellerRequest;
import br.com.onion.application.dto.response.SellerResponse;
import br.com.onion.domain.entity.Seller;

import java.util.UUID;

public final class SellerMapper {

    private SellerMapper() {}

    public static Seller toEntity(SellerRequest request) {
        return Seller.builder()
                .id(UUID.randomUUID().toString().replace("-", ""))
                .zipCodePrefix(request.getZipCodePrefix())
                .city(request.getCity())
                .state(request.getState())
                .build();
    }

    public static SellerResponse toResponse(Seller entity) {
        return SellerResponse.builder()
                .id(entity.getId())
                .zipCodePrefix(entity.getZipCodePrefix())
                .city(entity.getCity())
                .state(entity.getState())
                .build();
    }

    public static void updateEntity(Seller entity, SellerRequest request) {
        entity.setZipCodePrefix(request.getZipCodePrefix());
        entity.setCity(request.getCity());
        entity.setState(request.getState());
    }
}
