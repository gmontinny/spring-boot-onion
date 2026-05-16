package br.com.onion.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @Column(name = "product_id", length = 32)
    private String id;

    @Column(name = "product_category_name", length = 100)
    private String categoryName;

    @Column(name = "product_name_length")
    private Integer nameLength;

    @Column(name = "product_description_length")
    private Integer descriptionLength;

    @Column(name = "product_photos_qty")
    private Integer photosQty;

    @Column(name = "product_weight_g")
    private Integer weightG;

    @Column(name = "product_length_cm")
    private Integer lengthCm;

    @Column(name = "product_height_cm")
    private Integer heightCm;

    @Column(name = "product_width_cm")
    private Integer widthCm;
}
