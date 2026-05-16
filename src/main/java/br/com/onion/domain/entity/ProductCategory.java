package br.com.onion.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "product_category_translation")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategory {

    @Id
    @Column(name = "product_category_name", length = 100)
    private String categoryName;

    @Column(name = "product_category_name_english", length = 100)
    private String categoryNameEnglish;
}
