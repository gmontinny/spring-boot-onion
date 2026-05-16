package br.com.onion.domain.repository;

import br.com.onion.domain.entity.Geolocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface GeolocationRepository {
    Page<Geolocation> findAll(Pageable pageable);
    Optional<Geolocation> findById(Long id);
    Geolocation save(Geolocation geolocation);
    void deleteById(Long id);
    List<Geolocation> findByZipCodePrefix(String zipCodePrefix);
}
