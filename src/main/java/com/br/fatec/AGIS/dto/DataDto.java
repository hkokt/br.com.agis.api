package com.br.fatec.AGIS.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record DataDto(
		@NotNull LocalDate data,
		@NotBlank String descricao,
		@NotNull boolean ehFeriado
) {
	
}
