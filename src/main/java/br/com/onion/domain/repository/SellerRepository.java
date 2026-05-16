package br.com.onion.domain.repository;

import br.com.onion.domain.entity.Seller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface SellerRepository {
    Page<Seller> findAll(Pageable pageable);
    Optional<Seller> findById(String id);
    Seller save(Seller seller);
    void deleteById(String id);
}
