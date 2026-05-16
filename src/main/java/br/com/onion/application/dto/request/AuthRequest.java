package br.com.onion.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Payload de autenticação")
public class AuthRequest {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 100)
    @Schema(description = "Nome de usuário", example = "admin")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6)
    @Schema(description = "Senha do usuário (mínimo 6 caracteres)", example = "admin123")
    private String password;
}
