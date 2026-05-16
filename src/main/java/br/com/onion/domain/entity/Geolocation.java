package br.com.onion.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "geolocation")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Geolocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "geolocation_zip_code_prefix", length = 5)
    private String zipCodePrefix;

    @Column(name = "geolocation_lat", precision = 20, scale = 16)
    private BigDecimal lat;

    @Column(name = "geolocation_lng", precision = 20, scale = 16)
    private BigDecimal lng;

    @Column(name = "geolocation_city", length = 100)
    private String city;

    @Column(name = "geolocation_state", length = 2)
    private String state;
}
