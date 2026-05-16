package br.com.onion.presentation.controller;

import br.com.onion.application.dto.request.ProductRequest;
import br.com.onion.application.dto.response.ProductResponse;
import br.com.onion.application.service.ProductService;
import br.com.onion.presentation.assembler.ProductModelAssembler;
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
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Gerenciamento de produtos")
public class ProductController {

    private final ProductService productService;
    private final ProductModelAssembler assembler;

    @GetMapping
    @Operation(
            summary = "Listar produtos",
            description = "Retorna lista paginada de produtos com links HATEOAS",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            }
    )
    public ResponseEntity<PagedModel<ProductResponse>> findAll(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            @PageableDefault(size = 20) Pageable pageable) {
        Page<ProductResponse> page = productService.findAll(pageable);
        List<ProductResponse> content = page.getContent().stream().map(assembler::toModel).toList();
        PageMetadata metadata = new PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements());
        return ResponseEntity.ok(PagedModel.of(content, metadata));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar produto por ID",
            description = "Retorna um produto específico com links HATEOAS",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produto encontrado",
                            content = @Content(schema = @Schema(implementation = ProductResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            }
    )
    public ResponseEntity<ProductResponse> findById(
            @Parameter(description = "ID do produto", required = true) @PathVariable String id) {
        return ResponseEntity.ok(assembler.toModel(productService.findById(id)));
    }

    @PostMapping
    @Operation(
            summary = "Criar produto",
            description = "Cria um novo produto e retorna os dados com links HATEOAS",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produto criado com sucesso",
                            content = @Content(schema = @Schema(implementation = ProductResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            }
    )
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(assembler.toModel(productService.create(request)));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar produto",
            description = "Atualiza os dados de um produto existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso",
                            content = @Content(schema = @Schema(implementation = ProductResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            }
    )
    public ResponseEntity<ProductResponse> update(
            @Parameter(description = "ID do produto", required = true) @PathVariable String id,
            @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(assembler.toModel(productService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Remover produto",
            description = "Remove um produto pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Produto removido com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Produto não encontrado", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            }
    )
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do produto", required = true) @PathVariable String id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
