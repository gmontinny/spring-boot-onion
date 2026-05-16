package br.com.onion.presentation.controller;

import br.com.onion.application.dto.request.OrderItemRequest;
import br.com.onion.application.dto.response.OrderItemResponse;
import br.com.onion.application.service.OrderItemService;
import br.com.onion.presentation.assembler.OrderItemModelAssembler;
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
@RequestMapping("/api/order-items")
@RequiredArgsConstructor
@Tag(name = "Order Items", description = "Gerenciamento de itens do pedido")
public class OrderItemController {

    private final OrderItemService orderItemService;
    private final OrderItemModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar itens de pedido", description = "Retorna lista paginada de itens",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            })
    public ResponseEntity<PagedModel<OrderItemResponse>> findAll(@PageableDefault(size = 20) Pageable pageable) {
        Page<OrderItemResponse> page = orderItemService.findAll(pageable);
        List<OrderItemResponse> content = page.getContent().stream().map(assembler::toModel).toList();
        PageMetadata metadata = new PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements());
        return ResponseEntity.ok(PagedModel.of(content, metadata));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar item por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Item encontrado",
                            content = @Content(schema = @Schema(implementation = OrderItemResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Item não encontrado", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            })
    public ResponseEntity<OrderItemResponse> findById(
            @Parameter(description = "ID do item", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(orderItemService.findById(id)));
    }

    @GetMapping("/order/{orderId}")
    @Operation(summary = "Buscar itens por pedido",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            })
    public ResponseEntity<PagedModel<OrderItemResponse>> findByOrder(
            @Parameter(description = "ID do pedido", required = true) @PathVariable String orderId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<OrderItemResponse> page = orderItemService.findByOrderId(orderId, pageable);
        List<OrderItemResponse> content = page.getContent().stream().map(assembler::toModel).toList();
        PageMetadata metadata = new PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements());
        return ResponseEntity.ok(PagedModel.of(content, metadata));
    }

    @PostMapping
    @Operation(summary = "Criar item de pedido",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Item criado com sucesso",
                            content = @Content(schema = @Schema(implementation = OrderItemResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Pedido/Produto/Vendedor não encontrado", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            })
    public ResponseEntity<OrderItemResponse> create(@Valid @RequestBody OrderItemRequest request) {
        return ResponseEntity.ok(assembler.toModel(orderItemService.create(request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover item de pedido",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Item removido com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do item", required = true) @PathVariable Long id) {
        orderItemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
