package br.com.onion.presentation.controller;

import br.com.onion.application.dto.request.OrderPaymentRequest;
import br.com.onion.application.dto.response.OrderPaymentResponse;
import br.com.onion.application.service.OrderPaymentService;
import br.com.onion.presentation.assembler.OrderPaymentModelAssembler;
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
@RequestMapping("/api/order-payments")
@RequiredArgsConstructor
@Tag(name = "Order Payments", description = "Gerenciamento de pagamentos do pedido")
public class OrderPaymentController {

    private final OrderPaymentService orderPaymentService;
    private final OrderPaymentModelAssembler assembler;

    @GetMapping
    @Operation(summary = "Listar pagamentos", description = "Retorna lista paginada de pagamentos",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            })
    public ResponseEntity<PagedModel<OrderPaymentResponse>> findAll(@PageableDefault(size = 20) Pageable pageable) {
        Page<OrderPaymentResponse> page = orderPaymentService.findAll(pageable);
        List<OrderPaymentResponse> content = page.getContent().stream().map(assembler::toModel).toList();
        PageMetadata metadata = new PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements());
        return ResponseEntity.ok(PagedModel.of(content, metadata));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pagamento por ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pagamento encontrado",
                            content = @Content(schema = @Schema(implementation = OrderPaymentResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Pagamento não encontrado", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            })
    public ResponseEntity<OrderPaymentResponse> findById(
            @Parameter(description = "ID do pagamento", required = true) @PathVariable Long id) {
        return ResponseEntity.ok(assembler.toModel(orderPaymentService.findById(id)));
    }

    @GetMapping("/order/{orderId}")
    @Operation(summary = "Buscar pagamentos por pedido",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            })
    public ResponseEntity<PagedModel<OrderPaymentResponse>> findByOrder(
            @Parameter(description = "ID do pedido", required = true) @PathVariable String orderId,
            @PageableDefault(size = 20) Pageable pageable) {
        Page<OrderPaymentResponse> page = orderPaymentService.findByOrderId(orderId, pageable);
        List<OrderPaymentResponse> content = page.getContent().stream().map(assembler::toModel).toList();
        PageMetadata metadata = new PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements());
        return ResponseEntity.ok(PagedModel.of(content, metadata));
    }

    @PostMapping
    @Operation(summary = "Criar pagamento",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Pagamento criado com sucesso",
                            content = @Content(schema = @Schema(implementation = OrderPaymentResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            })
    public ResponseEntity<OrderPaymentResponse> create(@Valid @RequestBody OrderPaymentRequest request) {
        return ResponseEntity.ok(assembler.toModel(orderPaymentService.create(request)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover pagamento",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Pagamento removido com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            })
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do pagamento", required = true) @PathVariable Long id) {
        orderPaymentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
