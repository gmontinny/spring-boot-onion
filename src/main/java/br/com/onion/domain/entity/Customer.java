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
@Table(name = "customers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @Column(name = "customer_id", length = 32)
    private String id;

    @Column(name = "customer_unique_id", length = 32, nullable = false)
    private String uniqueId;

    @Column(name = "customer_zip_code_prefix", length = 5)
    private String zipCodePrefix;

    @Column(name = "customer_city", length = 100)
    private String city;

    @Column(name = "customer_state", length = 2)
    private String state;
}
