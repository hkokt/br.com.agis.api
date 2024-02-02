package com.br.fatec.AGIS.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.fatec.AGIS.dto.MatriculaDto;
import com.br.fatec.AGIS.model.Aluno;
import com.br.fatec.AGIS.model.Matricula;
import com.br.fatec.AGIS.model.Turma;
import com.br.fatec.AGIS.model.pk.MatriculaPk;
import com.br.fatec.AGIS.repository.AlunoRepository;
import com.br.fatec.AGIS.repository.MatriculaRepository;
import com.br.fatec.AGIS.repository.TurmaRepository;

@Service
public class MatriculaService {
	@Autowired
	private AlunoRepository alunoRepository;

	@Autowired
	private MatriculaRepository matriculaRepository;

	@Autowired
	private TurmaRepository turmaRepository;

	public List<Matricula> selectAllByRa(String ra) {
		return matriculaRepository.selectAllByRa(ra);
	}
	
	public List<Matricula> selectAllByCodTurma(Long codTurma) {
		return matriculaRepository.selectAllByCodTurma(codTurma);
	}

	public Matricula dispensar(MatriculaDto matriculaDto) {
		Optional<Aluno> aluno = alunoRepository.findById(matriculaDto.ra());
		Optional<Turma> turma = turmaRepository.findById(matriculaDto.codTurma());

		Optional<Matricula> matricula = matriculaRepository
				.findById(new MatriculaPk(aluno.get(), turma.get(), matriculaDto.ano(), matriculaDto.semestre()));
		
		var matriculaModel = matricula.get();
		matriculaModel.setSituacao("dispensado");
		
		return matriculaRepository.save(matriculaModel);
	}
}
