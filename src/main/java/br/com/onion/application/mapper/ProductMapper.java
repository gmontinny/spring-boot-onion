package br.com.onion.application.mapper;

import br.com.onion.application.dto.request.ProductRequest;
import br.com.onion.application.dto.response.ProductResponse;
import br.com.onion.domain.entity.Product;

import java.util.UUID;

public final class ProductMapper {

    private ProductMapper() {}

    public static Product toEntity(ProductRequest request) {
        return Product.builder()
                .id(UUID.randomUUID().toString().replace("-", ""))
                .categoryName(request.getCategoryName())
                .nameLength(request.getNameLength())
                .descriptionLength(request.getDescriptionLength())
                .photosQty(request.getPhotosQty())
                .weightG(request.getWeightG())
                .lengthCm(request.getLengthCm())
                .heightCm(request.getHeightCm())
                .widthCm(request.getWidthCm())
                .build();
    }

    public static ProductResponse toResponse(Product entity) {
        return ProductResponse.builder()
                .id(entity.getId())
                .categoryName(entity.getCategoryName())
                .nameLength(entity.getNameLength())
                .descriptionLength(entity.getDescriptionLength())
                .photosQty(entity.getPhotosQty())
                .weightG(entity.getWeightG())
                .lengthCm(entity.getLengthCm())
                .heightCm(entity.getHeightCm())
                .widthCm(entity.getWidthCm())
                .build();
    }

    public static void updateEntity(Product entity, ProductRequest request) {
        entity.setCategoryName(request.getCategoryName());
        entity.setNameLength(request.getNameLength());
        entity.setDescriptionLength(request.getDescriptionLength());
        entity.setPhotosQty(request.getPhotosQty());
        entity.setWeightG(request.getWeightG());
        entity.setLengthCm(request.getLengthCm());
        entity.setHeightCm(request.getHeightCm());
        entity.setWidthCm(request.getWidthCm());
    }
}
