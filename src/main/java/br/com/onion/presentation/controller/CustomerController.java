package br.com.onion.presentation.controller;

import br.com.onion.application.dto.request.CustomerRequest;
import br.com.onion.application.dto.response.CustomerResponse;
import br.com.onion.application.service.CustomerService;
import br.com.onion.presentation.assembler.CustomerModelAssembler;
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
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@Tag(name = "Customers", description = "Gerenciamento de clientes")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerModelAssembler assembler;

    @GetMapping
    @Operation(
            summary = "Listar clientes",
            description = "Retorna lista paginada de clientes com links HATEOAS",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            }
    )
    public ResponseEntity<PagedModel<CustomerResponse>> findAll(
            @Parameter(description = "Parâmetros de paginação (page, size, sort)")
            @PageableDefault(size = 20) Pageable pageable) {
        Page<CustomerResponse> page = customerService.findAll(pageable);
        List<CustomerResponse> content = page.getContent().stream().map(assembler::toModel).toList();
        PageMetadata metadata = new PageMetadata(page.getSize(), page.getNumber(), page.getTotalElements());
        return ResponseEntity.ok(PagedModel.of(content, metadata));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Buscar cliente por ID",
            description = "Retorna um cliente específico com links HATEOAS",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente encontrado",
                            content = @Content(schema = @Schema(implementation = CustomerResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            }
    )
    public ResponseEntity<CustomerResponse> findById(
            @Parameter(description = "ID do cliente", required = true) @PathVariable String id) {
        return ResponseEntity.ok(assembler.toModel(customerService.findById(id)));
    }

    @PostMapping
    @Operation(
            summary = "Criar cliente",
            description = "Cria um novo cliente e retorna os dados com links HATEOAS",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente criado com sucesso",
                            content = @Content(schema = @Schema(implementation = CustomerResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            }
    )
    public ResponseEntity<CustomerResponse> create(@Valid @RequestBody CustomerRequest request) {
        return ResponseEntity.ok(assembler.toModel(customerService.create(request)));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Atualizar cliente",
            description = "Atualiza os dados de um cliente existente",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso",
                            content = @Content(schema = @Schema(implementation = CustomerResponse.class))),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content),
                    @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            }
    )
    public ResponseEntity<CustomerResponse> update(
            @Parameter(description = "ID do cliente", required = true) @PathVariable String id,
            @Valid @RequestBody CustomerRequest request) {
        return ResponseEntity.ok(assembler.toModel(customerService.update(id, request)));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Remover cliente",
            description = "Remove um cliente pelo ID",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Cliente removido com sucesso"),
                    @ApiResponse(responseCode = "404", description = "Cliente não encontrado", content = @Content),
                    @ApiResponse(responseCode = "401", description = "Token não informado ou inválido", content = @Content)
            }
    )
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID do cliente", required = true) @PathVariable String id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
