package com.br.fatec.AGIS.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@lombok.Data
@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
public class Usuario {
	@Id
	@Column(nullable = false, unique = true, length = 11)
	private String cpf;
	
	@Column(nullable = false, length = 100)
	private String nome;
	
	@Column(nullable = false, name = "data_nasc", columnDefinition = "DATE")
	private LocalDate dataNasc;
	
	@Column(nullable = false, unique = true, name = "email_pessoal", length = 30)
	private String emailPessoal;
	
	@Column(nullable = false, unique = true, name = "email_corp", length = 30)
	private String emailCorp;	
	
	@Column(nullable = false, length = 20)
	private String situacao;
	
	@Column(nullable = false, unique = true, length = 30)
	private String senha;
}
