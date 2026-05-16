package br.com.onion.presentation.controller;

import br.com.onion.application.dto.request.OrderReviewRequest;
import br.com.onion.application.dto.response.OrderReviewResponse;
import br.com.onion.application.service.OrderReviewService;
import br.com.onion.presentation.assembler.OrderReviewModelAssembler;
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
@RequestMapping("/api/order-reviews")
@RequiredArgsConstructor
@Tag(name = "Order Reviews", description = "Gerenciamento de avaliações de pedidos")
public class OrderReviewController {

    private final OrderReviewService orderReviewService;
    private final OrderReviewModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar avaliações", description = "Retorna lista paginada de avaliações",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            })
    public ResponseEntity<PagedModel<OrderReviewResponse>> findAll(@PageableDefault(size = 20) Pageable pageable) {
        Page<OrderReviewResponse> page = orderReviewService.findAll(pageable);
        List<OrderReviewResponse> content = page.getContent().stream().map(assembler::toModel).toList();
        PageMetadata metadata = new PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements());
        return ResponseEntity.ok(PagedModel.of(content, metadata));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar avaliação por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Avaliação encontrada",
                            content = @Content(schema = @Schema(implementation = OrderReviewResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Avaliação não encontrada", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            })
    public ResponseEntity<OrderReviewResponse> findById(
            @Parameter(description = "ID da avaliação", required = true) @PathVariable String id) {
        return ResponseEntity.ok(assembler.toModel(orderReviewService.findById(id)));
    }

    @GetMapping("/order/{orderId}")
    @Operation(summary = "Buscar avaliações por pedido",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            })
    public ResponseEntity<PagedModel<OrderReviewResponse>> findByOrder(
            @Parameter(description = "ID do pedido", required = true) @PathVariable String orderId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<OrderReviewResponse> page = orderReviewService.findByOrderId(orderId, pageable);
        List<OrderReviewResponse> content = page.getContent().stream().map(assembler::toModel).toList();
        PageMetadata metadata = new PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements());
        return ResponseEntity.ok(PagedModel.of(content, metadata));
    }

    @PostMapping
    @Operation(summary = "Criar avaliação",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Avaliação criada com sucesso",
                            content = @Content(schema = @Schema(implementation = OrderReviewResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            })
    public ResponseEntity<OrderReviewResponse> create(@Valid @RequestBody OrderReviewRequest request) {
        return ResponseEntity.ok(assembler.toModel(orderReviewService.create(request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover avaliação",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Avaliação removida com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID da avaliação", required = true) @PathVariable String id) {
        orderReviewService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
