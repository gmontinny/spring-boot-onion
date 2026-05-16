package br.com.onion.presentation.assembler;

import br.com.onion.application.dto.response.CustomerResponse;
import br.com.onion.presentation.controller.CustomerController;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CustomerModelAssembler implements RepresentationModelAssembler<CustomerResponse, CustomerResponse> {

    @Override
    public CustomerResponse toModel(CustomerResponse response) {
        response.add(linkTo(methodOn(CustomerController.class).findById(response.getId())).withSelfRel());
        response.add(linkTo(methodOn(CustomerController.class).findAll(null)).withRel("customers"));
        return response;
    }
}
