package br.com.onion.application.service;

import br.com.onion.application.dto.request.SellerRequest;
import br.com.onion.application.dto.response.SellerResponse;
import br.com.onion.application.mapper.SellerMapper;
import br.com.onion.domain.entity.Seller;
import br.com.onion.domain.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SellerService {

    private final SellerRepository sellerRepository;

    public Page<SellerResponse> findAll(Pageable pageable) {
        return sellerRepository.findAll(pageable).map(SellerMapper::toResponse);
    }

    public SellerResponse findById(String id) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Seller not found: " + id));
        return SellerMapper.toResponse(seller);
    }

    @Transactional
    public SellerResponse create(SellerRequest request) {
        Seller seller = SellerMapper.toEntity(request);
        return SellerMapper.toResponse(sellerRepository.save(seller));
    }

    @Transactional
    public SellerResponse update(String id, SellerRequest request) {
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Seller not found: " + id));
        SellerMapper.updateEntity(seller, request);
        return SellerMapper.toResponse(sellerRepository.save(seller));
    }

    @Transactional
    public void delete(String id) {
        sellerRepository.deleteById(id);
    }
}
