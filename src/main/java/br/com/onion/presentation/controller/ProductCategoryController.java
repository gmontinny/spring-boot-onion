package br.com.onion.presentation.controller;

import br.com.onion.application.dto.request.ProductCategoryRequest;
import br.com.onion.application.dto.response.ProductCategoryResponse;
import br.com.onion.application.service.ProductCategoryService;
import br.com.onion.presentation.assembler.ProductCategoryModelAssembler;
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
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Product Categories", description = "Gerenciamento de tradução de categorias de produtos")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;
    private final ProductCategoryModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar categorias", description = "Retorna lista paginada de categorias com tradução",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            })
    public ResponseEntity<PagedModel<ProductCategoryResponse>> findAll(@PageableDefault(size = 20) Pageable pageable) {
        Page<ProductCategoryResponse> page = productCategoryService.findAll(pageable);
        List<ProductCategoryResponse> content = page.getContent().stream().map(assembler::toModel).toList();
        PageMetadata metadata = new PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements());
        return ResponseEntity.ok(PagedModel.of(content, metadata));
    }

    @GetMapping("/{categoryName}")
    @Operation(summary = "Buscar categoria por nome",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Categoria encontrada",
                            content = @Content(schema = @Schema(implementation = ProductCategoryResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Categoria não encontrada", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            })
    public ResponseEntity<ProductCategoryResponse> findById(
            @Parameter(description = "Nome da categoria", required = true) @PathVariable String categoryName) {
        return ResponseEntity.ok(assembler.toModel(productCategoryService.findById(categoryName)));
    }

    @PostMapping
    @Operation(summary = "Criar categoria",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Categoria criada com sucesso",
                            content = @Content(schema = @Schema(implementation = ProductCategoryResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            })
    public ResponseEntity<ProductCategoryResponse> create(@Valid @RequestBody ProductCategoryRequest request) {
        return ResponseEntity.ok(assembler.toModel(productCategoryService.create(request)));
    }

    @PutMapping("/{categoryName}")
    @Operation(summary = "Atualizar categoria",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso",
                            content = @Content(schema = @Schema(implementation = ProductCategoryResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Categoria não encontrada", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            })
    public ResponseEntity<ProductCategoryResponse> update(
            @Parameter(description = "Nome da categoria", required = true) @PathVariable String categoryName,
            @Valid @RequestBody ProductCategoryRequest request) {
        return ResponseEntity.ok(assembler.toModel(productCategoryService.update(categoryName, request)));
    }

    @DeleteMapping("/{categoryName}")
    @Operation(summary = "Remover categoria",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Categoria removida com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            })
    public ResponseEntity<Void> delete(
            @Parameter(description = "Nome da categoria", required = true) @PathVariable String categoryName) {
        productCategoryService.delete(categoryName);
        return ResponseEntity.noContent().build();
    }
}
