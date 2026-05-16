package br.com.onion.domain.repository;

import br.com.onion.domain.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductRepository {
    Page<Product> findAll(Pageable pageable);
    Optional<Product> findById(String id);
    Product save(Product product);
    void deleteById(String id);
}
