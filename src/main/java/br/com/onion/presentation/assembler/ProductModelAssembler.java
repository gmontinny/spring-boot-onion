package br.com.onion.presentation.assembler;

import br.com.onion.application.dto.response.ProductResponse;
import br.com.onion.presentation.controller.ProductController;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ProductModelAssembler implements RepresentationModelAssembler<ProductResponse, ProductResponse> {

    @Override
    public ProductResponse toModel(ProductResponse response) {
        response.add(linkTo(methodOn(ProductController.class).findById(response.getId())).withSelfRel());
        response.add(linkTo(methodOn(ProductController.class).findAll(null)).withRel("products"));
        return response;
    }
}
