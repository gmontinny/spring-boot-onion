package br.com.onion.presentation.assembler;

import br.com.onion.application.dto.response.OrderReviewResponse;
import br.com.onion.presentation.controller.OrderReviewController;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class OrderReviewModelAssembler implements RepresentationModelAssembler<OrderReviewResponse, OrderReviewResponse> {

    @Override
    public OrderReviewResponse toModel(OrderReviewResponse response) {
        response.add(linkTo(methodOn(OrderReviewController.class).findById(response.getId())).withSelfRel());
        response.add(linkTo(methodOn(OrderReviewController.class).findAll(null)).withRel("order-reviews"));
        return response;
    }
}
