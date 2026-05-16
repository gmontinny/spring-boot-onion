package br.com.onion.application.service;

import br.com.onion.application.dto.request.GeolocationRequest;
import br.com.onion.application.dto.response.GeolocationResponse;
import br.com.onion.application.mapper.GeolocationMapper;
import br.com.onion.domain.entity.Geolocation;
import br.com.onion.domain.repository.GeolocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GeolocationService {

    private final GeolocationRepository geolocationRepository;

    public Page<GeolocationResponse> findAll(Pageable pageable) {
        return geolocationRepository.findAll(pageable).map(GeolocationMapper::toResponse);
    }

    public GeolocationResponse findById(Long id) {
        Geolocation geo = geolocationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Geolocation not found: " + id));
        return GeolocationMapper.toResponse(geo);
    }

    public List<GeolocationResponse> findByZipCode(String zipCodePrefix) {
        return geolocationRepository.findByZipCodePrefix(zipCodePrefix).stream()
                .map(GeolocationMapper::toResponse).toList();
    }

    @Transactional
    public GeolocationResponse create(GeolocationRequest request) {
        Geolocation geo = GeolocationMapper.toEntity(request);
        return GeolocationMapper.toResponse(geolocationRepository.save(geo));
    }

    @Transactional
    public GeolocationResponse update(Long id, GeolocationRequest request) {
        Geolocation geo = geolocationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Geolocation not found: " + id));
        GeolocationMapper.updateEntity(geo, request);
        return GeolocationMapper.toResponse(geolocationRepository.save(geo));
    }

    @Transactional
    public void delete(Long id) {
        geolocationRepository.deleteById(id);
    }
}
