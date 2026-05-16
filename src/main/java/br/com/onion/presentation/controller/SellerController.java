package br.com.onion.presentation.controller;

import br.com.onion.application.dto.request.SellerRequest;
import br.com.onion.application.dto.response.SellerResponse;
import br.com.onion.application.service.SellerService;
import br.com.onion.presentation.assembler.SellerModelAssembler;
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
@RequestMapping("/api/sellers")
@RequiredArgsConstructor
@Tag(name = "Sellers", description = "Gerenciamento de vendedores")
public class SellerController {

    private final SellerService sellerService;
    private final SellerModelAssembler assembler;

    @GetMapping
    @Operation(
            summary = "Listar vendedores",
            description = "Retorna lista paginada de vendedores com links HATEOAS",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            }
    )
    public ResponseEntity<PagedModel<SellerResponse>> findAll(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            @PageableDefault(size = 20) Pageable pageable) {
        Page<SellerResponse> page = sellerService.findAll(pageable);
        List<SellerResponse> content = page.getContent().stream().map(assembler::toModel).toList();
        PageMetadata metadata = new PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements());
        return ResponseEntity.ok(PagedModel.of(content, metadata));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar vendedor por ID",
            description = "Retorna um vendedor específico com links HATEOAS",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Vendedor encontrado",
                            content = @Content(schema = @Schema(implementation = SellerResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Vendedor não encontrado", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            }
    )
    public ResponseEntity<SellerResponse> findById(
            @Parameter(description = "ID do vendedor", required = true) @PathVariable String id) {
        return ResponseEntity.ok(assembler.toModel(sellerService.findById(id)));
    }

    @PostMapping
    @Operation(
            summary = "Criar vendedor",
            description = "Cria um novo vendedor e retorna os dados com links HATEOAS",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Vendedor criado com sucesso",
                            content = @Content(schema = @Schema(implementation = SellerResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            }
    )
    public ResponseEntity<SellerResponse> create(@Valid @RequestBody SellerRequest request) {
        return ResponseEntity.ok(assembler.toModel(sellerService.create(request)));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar vendedor",
            description = "Atualiza os dados de um vendedor existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Vendedor atualizado com sucesso",
                            content = @Content(schema = @Schema(implementation = SellerResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Vendedor não encontrado", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            }
    )
    public ResponseEntity<SellerResponse> update(
            @Parameter(description = "ID do vendedor", required = true) @PathVariable String id,
            @Valid @RequestBody SellerRequest request) {
        return ResponseEntity.ok(assembler.toModel(sellerService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Remover vendedor",
            description = "Remove um vendedor pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Vendedor removido com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Vendedor não encontrado", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            }
    )
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do vendedor", required = true) @PathVariable String id) {
        sellerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
