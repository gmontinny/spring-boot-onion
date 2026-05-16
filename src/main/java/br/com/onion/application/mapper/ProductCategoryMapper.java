package br.com.onion.application.mapper;

import br.com.onion.application.dto.request.ProductCategoryRequest;
import br.com.onion.application.dto.response.ProductCategoryResponse;
import br.com.onion.domain.entity.ProductCategory;

public final class ProductCategoryMapper {

    private ProductCategoryMapper() {}

    public static ProductCategory toEntity(ProductCategoryRequest request) {
        return ProductCategory.builder()
                .categoryName(request.getCategoryName())
                .categoryNameEnglish(request.getCategoryNameEnglish())
                .build();
    }

    public static ProductCategoryResponse toResponse(ProductCategory entity) {
        return ProductCategoryResponse.builder()
                .categoryName(entity.getCategoryName())
                .categoryNameEnglish(entity.getCategoryNameEnglish())
                .build();
    }

    public static void updateEntity(ProductCategory entity, ProductCategoryRequest request) {
        entity.setCategoryNameEnglish(request.getCategoryNameEnglish());
    }
}
