package br.com.onion.presentation.controller;

import br.com.onion.application.dto.request.GeolocationRequest;
import br.com.onion.application.dto.response.GeolocationResponse;
import br.com.onion.application.service.GeolocationService;
import br.com.onion.presentation.assembler.GeolocationModelAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.PagedModel.PageMetadata;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/geolocations")
@RequiredArgsConstructor
@Tag(name = "Geolocations", description = "Gerenciamento de geolocalização por CEP")
public class GeolocationController {

    private final GeolocationService geolocationService;
    private final GeolocationModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar geolocalizações", description = "Retorna lista paginada de geolocalizações",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            })
    public ResponseEntity<PagedModel<GeolocationResponse>> findAll(@PageableDefault(size = 20) Pageable pageable) {
        Page<GeolocationResponse> page = geolocationService.findAll(pageable);
        List<GeolocationResponse> content = page.getContent().stream().map(assembler::toModel).toList();
        PageMetadata metadata = new PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements());
        return ResponseEntity.ok(PagedModel.of(content, metadata));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar geolocalização por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Geolocalização encontrada",
                            content = @Content(schema = @Schema(implementation = GeolocationResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Não encontrada", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            })
    public ResponseEntity<GeolocationResponse> findById(
            @Parameter(description = "ID da geolocalização", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(geolocationService.findById(id)));
    }

    @GetMapping("/zipcode/{zipCodePrefix}")
    @Operation(summary = "Buscar geolocalizações por CEP",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            })
    public ResponseEntity<List<GeolocationResponse>> findByZipCode(
            @Parameter(description = "Prefixo do CEP", required = true) @PathVariable String zipCodePrefix) {
        List<GeolocationResponse> result = geolocationService.findByZipCode(zipCodePrefix).stream()
                .map(assembler::toModel).toList();
        return ResponseEntity.ok(result);
    }

    @PostMapping
    @Operation(summary = "Criar geolocalização",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Criada com sucesso",
                            content = @Content(schema = @Schema(implementation = GeolocationResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            })
    public ResponseEntity<GeolocationResponse> create(@Valid @RequestBody GeolocationRequest request) {
        return ResponseEntity.ok(assembler.toModel(geolocationService.create(request)));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar geolocalização",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Atualizada com sucesso",
                            content = @Content(schema = @Schema(implementation = GeolocationResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Não encontrada", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            })
    public ResponseEntity<GeolocationResponse> update(
            @Parameter(description = "ID da geolocalização", required = true) @PathVariable Long id,
            @Valid @RequestBody GeolocationRequest request) {
        return ResponseEntity.ok(assembler.toModel(geolocationService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover geolocalização",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Removida com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID da geolocalização", required = true) @PathVariable Long id) {
        geolocationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
