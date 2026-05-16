package br.com.onion.presentation.assembler;

import br.com.onion.application.dto.response.SellerResponse;
import br.com.onion.presentation.controller.SellerController;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class SellerModelAssembler implements RepresentationModelAssembler<SellerResponse, SellerResponse> {

    @Override
    public SellerResponse toModel(SellerResponse response) {
        response.add(linkTo(methodOn(SellerController.class).findById(response.getId())).withSelfRel());
        response.add(linkTo(methodOn(SellerController.class).findAll(null)).withRel("sellers"));
        return response;
    }
}
