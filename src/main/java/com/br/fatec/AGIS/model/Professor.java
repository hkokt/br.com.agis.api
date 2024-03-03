package com.br.fatec.AGIS.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@lombok.Data
@Entity
@Table
@Inheritance(strategy = InheritanceType.JOINED)
public class Professor{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(nullable = false)
	private Long cod;
	
	@Column(nullable = false, length = 100)
	private String titulacao;
	
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	@OneToOne(cascade = CascadeType.ALL, targetEntity = Usuario.class, fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name = "cpf", unique = true)
	private Usuario usuario;
}
