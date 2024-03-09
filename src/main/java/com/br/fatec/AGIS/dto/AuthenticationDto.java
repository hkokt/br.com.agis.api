package com.br.fatec.AGIS.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthenticationDto(
        @NotBlank String login,
        @NotBlank String password
) {
}
