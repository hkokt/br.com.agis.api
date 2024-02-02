package com.br.fatec.AGIS.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@lombok.Data
@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
public class Aluno {
	@Id
	@Column(nullable = false, unique = true, length = 15)
	private String ra;
	
	@Column(nullable = false, name = "nome_social", length = 100)
	private String nomeSocial;
	
	@Column(nullable = false, name = "data_conc_2grau", columnDefinition = "DATE")
	private LocalDate dataConc2grau;
	
	@Column(nullable = false, name = "inst_conc_2grau", length = 100)
	private String instConc2grau;
	
	@Column(nullable = false, name = "posicao_vestibular")
	private int ptVestibular;
	
	@Column(nullable = false, name = "pontuacao_vestibular")
	private int posVestibular;
	
	@Column(nullable = false, name = "data_matricula", columnDefinition = "DATE")
	private LocalDate dataMatricula;

	@Column(nullable = false, name = "data_limite_matricula", columnDefinition = "DATE")
	private LocalDate dataLimiteMatricula;
	
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Curso.class, fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name = "cod_curso")
	private Curso curso;
	
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	@OneToOne(cascade = CascadeType.ALL, targetEntity = Usuario.class, fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name = "cpf", unique = true)
	private Usuario usuario;
}
