package br.com.onion.presentation.assembler;

import br.com.onion.application.dto.response.OrderResponse;
import br.com.onion.presentation.controller.OrderController;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<OrderResponse, OrderResponse> {

    @Override
    public OrderResponse toModel(OrderResponse response) {
        response.add(linkTo(methodOn(OrderController.class).findById(response.getId())).withSelfRel());
        response.add(linkTo(methodOn(OrderController.class).findAll(null)).withRel("orders"));
        return response;
    }
}
