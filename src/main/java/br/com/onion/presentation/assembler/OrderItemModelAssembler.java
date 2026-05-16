package br.com.onion.presentation.assembler;

import br.com.onion.application.dto.response.OrderItemResponse;
import br.com.onion.presentation.controller.OrderItemController;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderItemModelAssembler implements RepresentationModelAssembler<OrderItemResponse, OrderItemResponse> {

    @Override
    public OrderItemResponse toModel(OrderItemResponse response) {
        response.add(linkTo(methodOn(OrderItemController.class).findById(response.getId())).withSelfRel());
        response.add(linkTo(methodOn(OrderItemController.class).findAll(null)).withRel("order-items"));
        return response;
    }
}
