package com.br.fatec.AGIS.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import security.UserRole;

public record SecretarioDto(
		@NotBlank String cpf,
		@NotBlank String senha,
		UserRole role,
		@NotBlank String nome,
		@NotNull LocalDate dataNasc,
		@NotBlank String emailPessoal
) {

}
