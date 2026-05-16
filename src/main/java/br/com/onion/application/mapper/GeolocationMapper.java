package br.com.onion.application.mapper;

import br.com.onion.application.dto.request.GeolocationRequest;
import br.com.onion.application.dto.response.GeolocationResponse;
import br.com.onion.domain.entity.Geolocation;

public final class GeolocationMapper {

    private GeolocationMapper() {}

    public static Geolocation toEntity(GeolocationRequest request) {
        return Geolocation.builder()
                .zipCodePrefix(request.getZipCodePrefix())
                .lat(request.getLat())
                .lng(request.getLng())
                .city(request.getCity())
                .state(request.getState())
                .build();
    }

    public static GeolocationResponse toResponse(Geolocation entity) {
        return GeolocationResponse.builder()
                .id(entity.getId())
                .zipCodePrefix(entity.getZipCodePrefix())
                .lat(entity.getLat())
                .lng(entity.getLng())
                .city(entity.getCity())
                .state(entity.getState())
                .build();
    }

    public static void updateEntity(Geolocation entity, GeolocationRequest request) {
        entity.setZipCodePrefix(request.getZipCodePrefix());
        entity.setLat(request.getLat());
        entity.setLng(request.getLng());
        entity.setCity(request.getCity());
        entity.setState(request.getState());
    }
}
