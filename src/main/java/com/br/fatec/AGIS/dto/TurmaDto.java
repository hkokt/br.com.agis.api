package com.br.fatec.AGIS.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TurmaDto(
		@NotBlank String horarioInicio,
		@NotBlank String horarioFim,
		@NotBlank String diaDaSemana,
		@NotBlank String situacao,
		@NotNull Long codDisciplina,
		@NotNull Long codProfessor,
		@NotNull Long codGradeCurricular
) {

}
