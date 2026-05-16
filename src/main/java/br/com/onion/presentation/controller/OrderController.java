package br.com.onion.presentation.controller;

import br.com.onion.application.dto.request.OrderRequest;
import br.com.onion.application.dto.response.OrderResponse;
import br.com.onion.application.service.OrderService;
import br.com.onion.presentation.assembler.OrderModelAssembler;
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
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "Orders", description = "Gerenciamento de pedidos")
public class OrderController {

    private final OrderService orderService;
    private final OrderModelAssembler assembler;

    @GetMapping
    @Operation(
            summary = "Listar pedidos",
            description = "Retorna lista paginada de pedidos com links HATEOAS",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            }
    )
    public ResponseEntity<PagedModel<OrderResponse>> findAll(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            @PageableDefault(size = 20) Pageable pageable) {
        Page<OrderResponse> page = orderService.findAll(pageable);
        List<OrderResponse> content = page.getContent().stream().map(assembler::toModel).toList();
        PageMetadata metadata = new PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements());
        return ResponseEntity.ok(PagedModel.of(content, metadata));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar pedido por ID",
            description = "Retorna um pedido específico com links HATEOAS",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pedido encontrado",
                            content = @Content(schema = @Schema(implementation = OrderResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            }
    )
    public ResponseEntity<OrderResponse> findById(
            @Parameter(description = "ID do pedido", required = true) @PathVariable String id) {
        return ResponseEntity.ok(assembler.toModel(orderService.findById(id)));
    }

    @GetMapping("/customer/{customerId}")
    @Operation(
            summary = "Buscar pedidos por cliente",
            description = "Retorna lista paginada de pedidos de um cliente específico",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            }
    )
    public ResponseEntity<PagedModel<OrderResponse>> findByCustomer(
            @Parameter(description = "ID do cliente", required = true) @PathVariable String customerId,
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            @PageableDefault(size = 20) Pageable pageable) {
        Page<OrderResponse> page = orderService.findByCustomerId(customerId, pageable);
        List<OrderResponse> content = page.getContent().stream().map(assembler::toModel).toList();
        PageMetadata metadata = new PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements());
        return ResponseEntity.ok(PagedModel.of(content, metadata));
    }

    @PostMapping
    @Operation(
            summary = "Criar pedido",
            description = "Cria um novo pedido associado a um cliente existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pedido criado com sucesso",
                            content = @Content(schema = @Schema(implementation = OrderResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            }
    )
    public ResponseEntity<OrderResponse> create(@Valid @RequestBody OrderRequest request) {
        return ResponseEntity.ok(assembler.toModel(orderService.create(request)));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Remover pedido",
            description = "Remove um pedido pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Pedido removido com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            }
    )
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do pedido", required = true) @PathVariable String id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
