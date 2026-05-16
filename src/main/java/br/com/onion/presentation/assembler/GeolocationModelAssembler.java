package br.com.onion.presentation.assembler;

import br.com.onion.application.dto.response.GeolocationResponse;
import br.com.onion.presentation.controller.GeolocationController;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class GeolocationModelAssembler implements RepresentationModelAssembler<GeolocationResponse, GeolocationResponse> {

    @Override
    public GeolocationResponse toModel(GeolocationResponse response) {
        response.add(linkTo(methodOn(GeolocationController.class).findById(response.getId())).withSelfRel());
        response.add(linkTo(methodOn(GeolocationController.class).findAll(null)).withRel("geolocations"));
        return response;
    }
}
