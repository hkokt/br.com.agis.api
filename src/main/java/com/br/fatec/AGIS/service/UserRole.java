package com.br.fatec.AGIS.service;

import lombok.Getter;
import lombok.Setter;


public enum UserRole {
	SECRETARIO("secretario"),
	PROFESSOR("professor"),
	ALUNO("aluno");
	
	@Getter
	@Setter
	private String role;
	
	private UserRole(String role) {
		this.role = role;
	}
}
