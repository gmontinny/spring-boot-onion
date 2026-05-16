package br.com.onion.infrastructure.repository;

import br.com.onion.domain.entity.Geolocation;
import br.com.onion.domain.repository.GeolocationRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaGeolocationRepository extends JpaRepository<Geolocation, Long>, GeolocationRepository {
    List<Geolocation> findByZipCodePrefix(String zipCodePrefix);
}
