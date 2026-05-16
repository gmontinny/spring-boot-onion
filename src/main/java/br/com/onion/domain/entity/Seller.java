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
@Table(name = "sellers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Seller {

    @Id
    @Column(name = "seller_id", length = 32)
    private String id;

    @Column(name = "seller_zip_code_prefix", length = 5)
    private String zipCodePrefix;

    @Column(name = "seller_city", length = 100)
    private String city;

    @Column(name = "seller_state", length = 2)
    private String state;
}
