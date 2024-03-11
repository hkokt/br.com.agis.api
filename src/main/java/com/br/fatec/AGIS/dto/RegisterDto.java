package com.br.fatec.AGIS.dto;

import com.br.fatec.AGIS.model.UserRole;
import jakarta.validation.constraints.NotBlank;

public record RegisterDto(
        @NotBlank String login,
        @NotBlank String password,
        UserRole role
) {
}
