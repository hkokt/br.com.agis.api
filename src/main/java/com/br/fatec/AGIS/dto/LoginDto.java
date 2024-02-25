package com.br.fatec.AGIS.dto;

import jakarta.validation.constraints.NotBlank;

public record LoginDto(
	@NotBlank String cpf,
	@NotBlank String senha
) {
	
}
