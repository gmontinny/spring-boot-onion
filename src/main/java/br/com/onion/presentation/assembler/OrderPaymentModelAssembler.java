package br.com.onion.presentation.assembler;

import br.com.onion.application.dto.response.OrderPaymentResponse;
import br.com.onion.presentation.controller.OrderPaymentController;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderPaymentModelAssembler implements RepresentationModelAssembler<OrderPaymentResponse, OrderPaymentResponse> {

    @Override
    public OrderPaymentResponse toModel(OrderPaymentResponse response) {
        response.add(linkTo(methodOn(OrderPaymentController.class).findById(response.getId())).withSelfRel());
        response.add(linkTo(methodOn(OrderPaymentController.class).findAll(null)).withRel("order-payments"));
        return response;
    }
}
