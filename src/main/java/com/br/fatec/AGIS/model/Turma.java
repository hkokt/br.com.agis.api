package com.br.fatec.AGIS.model;

import com.br.fatec.AGIS.dto.TurmaDto;
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
@Table
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor
public class Turma {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long cod;
	
	@Column(nullable = false, name = "horario_inicio")
	private String horarioInicio;
	
	@Column(nullable = false, name = "horario_fim")
	private String horarioFim;
	
	@Column(nullable = false, name = "dia_da_semana", length = 20)
	private String diaDaSemana;
	
	@Column(nullable = false, length = 20)
	private String situacao;
	
	@Column(length = 50, name = "metodo_avaliativo")
	private String metodoAvaliativo;
	
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Disciplina.class, fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name = "cod_disciplina")
	private Disciplina disciplina;
	
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = Professor.class, fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name = "cod_professor")
	private Professor professor;
	
	@JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
	@ManyToOne(cascade = CascadeType.ALL, targetEntity = GradeCurricular.class, fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name = "cod_grade")
	private GradeCurricular gradeCurricular;
	
	public Turma(TurmaDto turmaDto, Disciplina disciplina, Professor professor, GradeCurricular gradeCurricular) {
		this.horarioInicio = turmaDto.horarioInicio();
		this.horarioFim = turmaDto.horarioFim();
		this.diaDaSemana = turmaDto.diaDaSemana();
		this.disciplina = disciplina;
		this.professor = professor;
		this.situacao = "aberta";
		this.gradeCurricular = gradeCurricular;
	}
	
	public Turma(Turma turma, TurmaDto turmaDto, Disciplina disciplina, Professor professor) {
		this.cod = turma.getCod();
		this.horarioInicio = turmaDto.horarioInicio();
		this.horarioFim = turmaDto.horarioFim();
		this.diaDaSemana = turmaDto.diaDaSemana();
		this.disciplina = disciplina;
		this.professor = professor;
		this.situacao = turmaDto.situacao();
		this.gradeCurricular = turma.getGradeCurricular();
	}

}
