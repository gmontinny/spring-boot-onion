package br.com.onion.infrastructure.repository;

import br.com.onion.domain.entity.Product;
import br.com.onion.domain.repository.ProductRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProductRepository extends JpaRepository<Product, String>, ProductRepository {
}
