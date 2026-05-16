package br.com.onion.infrastructure.repository;

import br.com.onion.domain.entity.ProductCategory;
import br.com.onion.domain.repository.ProductCategoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaProductCategoryRepository extends JpaRepository<ProductCategory, String>, ProductCategoryRepository {
}
