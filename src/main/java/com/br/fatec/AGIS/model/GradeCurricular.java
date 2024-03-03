package com.br.fatec.AGIS.model;

import com.br.fatec.AGIS.dto.GradeCurricularDto;
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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;

@lombok.Data
@Entity
@Table(name = "Grade_Curricular")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
public class GradeCurricular {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long cod;
	
	@Column(nullable = false)
	private int ano; 
	
	@Column(nullable = false)
	private int semestre; 
	
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Curso.class, fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name = "cod_curso")
	private Curso curso;

	public GradeCurricular(GradeCurricularDto gradeCurricularDto, Curso curso) {
		this.ano = gradeCurricularDto.ano();
		this.semestre = gradeCurricularDto.semestre();
		this.curso = curso;
	}
}
