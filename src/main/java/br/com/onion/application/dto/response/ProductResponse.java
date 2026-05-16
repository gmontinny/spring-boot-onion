package br.com.onion.application.dto.response;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class ProductResponse extends RepresentationModel<ProductResponse> {
    private String id;
    private String categoryName;
    private Integer nameLength;
    private Integer descriptionLength;
    private Integer photosQty;
    private Integer weightG;
    private Integer lengthCm;
    private Integer heightCm;
    private Integer widthCm;
}
