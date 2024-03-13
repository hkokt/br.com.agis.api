package com.br.fatec.AGIS.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.fatec.AGIS.dto.TurmaDto;
import com.br.fatec.AGIS.model.Disciplina;
import com.br.fatec.AGIS.model.GradeCurricular;
import com.br.fatec.AGIS.model.Professor;
import com.br.fatec.AGIS.model.Turma;
import com.br.fatec.AGIS.repository.DisciplinaRepository;
import com.br.fatec.AGIS.repository.GradeCurricularRepository;
import com.br.fatec.AGIS.repository.ProfessorRepository;
import com.br.fatec.AGIS.repository.TurmaRepository;

@Service
public class TurmaService {
	@Autowired
	private DisciplinaRepository disciplinaRepository;

	@Autowired
	private GradeCurricularRepository gradeCurricularRepository;

	@Autowired
	private TurmaRepository turmaRepository;

	@Autowired
	private ProfessorRepository professorRepository;

	public List<Turma> selectAllFromGrade(Long codGrade) {
		return turmaRepository.selectAllFromGrade(codGrade);
	}
	
	public List<Turma> selectAllFromProf(Long codProfessor) {
		return turmaRepository.selectAllFromProf(codProfessor);
	}
	
	public Turma selectById(Long cod) throws Exception {
		Optional<Turma> turma = turmaRepository.findById(cod);

		if (turma.isEmpty()) {
			throw new Exception("Disciplina não registrada");
		}

		return turma.get();
	}

	public Turma insert(TurmaDto turmaDto) {
		Optional<Disciplina> disciplina = disciplinaRepository.findById(turmaDto.codDisciplina());
		Optional<Professor> professor = professorRepository.findById(turmaDto.codProfessor());
		Optional<GradeCurricular> gradeCurricular = gradeCurricularRepository.findById(turmaDto.codGradeCurricular());

		var turmaModel = new Turma(turmaDto, disciplina.get(), professor.get(), gradeCurricular.get());

		return turmaRepository.save(turmaModel);
	}
	
	public Turma update(Long id, TurmaDto turmaDto) throws Exception {
		Optional<Turma> turma = turmaRepository.findById(id);
		Optional<Disciplina> disciplina = disciplinaRepository.findById(turmaDto.codDisciplina());
		Optional<Professor> professor = professorRepository.findById(turmaDto.codProfessor());
		
		if (turma.isEmpty()) {
			throw new Exception("Curso não registrado");
		}

		var turmaModel = new Turma(turma.get(), turmaDto, disciplina.get(), professor.get());

		return turmaRepository.save(turmaModel);
	}
	
	public Turma delete(Long id) throws Exception {
		Optional<Turma> turma = turmaRepository.findById(id);
		if (turma.isEmpty()) {
			throw new Exception("Curso não registrado");
		}

		var turmaModel = turma.get();
		turmaModel.setDisciplina(null);
		turmaModel.setProfessor(null);
		turmaModel.setGradeCurricular(null);
		turmaRepository.delete(turmaModel);

		return turmaModel;
	}

	public Turma insertMetodoAvalitivo(Long id, String metodo) {
		Optional<Turma> turma = turmaRepository.findById(id);
		var turmaModel = turma.get();
		
		turmaModel.setMetodoAvaliativo(metodo);
		turmaRepository.save(turmaModel);
		
		return turmaModel;
	}
}
