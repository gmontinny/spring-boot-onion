package br.com.onion.application.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class ProductCategoryResponse extends RepresentationModel<ProductCategoryResponse> {
    private String categoryName;
    private String categoryNameEnglish;
}
