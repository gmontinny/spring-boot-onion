package br.com.onion.application.service;

import br.com.onion.application.dto.request.ProductCategoryRequest;
import br.com.onion.application.dto.response.ProductCategoryResponse;
import br.com.onion.application.mapper.ProductCategoryMapper;
import br.com.onion.domain.entity.ProductCategory;
import br.com.onion.domain.repository.ProductCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    public Page<ProductCategoryResponse> findAll(Pageable pageable) {
        return productCategoryRepository.findAll(pageable).map(ProductCategoryMapper::toResponse);
    }

    public ProductCategoryResponse findById(String categoryName) {
        ProductCategory category = productCategoryRepository.findById(categoryName)
                .orElseThrow(() -> new EntityNotFoundException("Category not found: " + categoryName));
        return ProductCategoryMapper.toResponse(category);
    }

    @Transactional
    public ProductCategoryResponse create(ProductCategoryRequest request) {
        ProductCategory category = ProductCategoryMapper.toEntity(request);
        return ProductCategoryMapper.toResponse(productCategoryRepository.save(category));
    }

    @Transactional
    public ProductCategoryResponse update(String categoryName, ProductCategoryRequest request) {
        ProductCategory category = productCategoryRepository.findById(categoryName)
                .orElseThrow(() -> new EntityNotFoundException("Category not found: " + categoryName));
        ProductCategoryMapper.updateEntity(category, request);
        return ProductCategoryMapper.toResponse(productCategoryRepository.save(category));
    }

    @Transactional
    public void delete(String categoryName) {
        productCategoryRepository.deleteById(categoryName);
    }
}
