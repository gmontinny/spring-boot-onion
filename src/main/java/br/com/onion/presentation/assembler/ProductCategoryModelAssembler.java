package br.com.onion.presentation.assembler;

import br.com.onion.application.dto.response.ProductCategoryResponse;
import br.com.onion.presentation.controller.ProductCategoryController;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductCategoryModelAssembler implements RepresentationModelAssembler<ProductCategoryResponse, ProductCategoryResponse> {

    @Override
    public ProductCategoryResponse toModel(ProductCategoryResponse response) {
        response.add(linkTo(methodOn(ProductCategoryController.class).findById(response.getCategoryName())).withSelfRel());
        response.add(linkTo(methodOn(ProductCategoryController.class).findAll(null)).withRel("categories"));
        return response;
    }
}
