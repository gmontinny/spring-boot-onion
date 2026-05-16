package br.com.onion.domain.repository;

import br.com.onion.domain.entity.ProductCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ProductCategoryRepository {
    Page<ProductCategory> findAll(Pageable pageable);
    Optional<ProductCategory> findById(String categoryName);
    ProductCategory save(ProductCategory productCategory);
    void deleteById(String categoryName);
}
