package com.br.fatec.AGIS.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.fatec.AGIS.dto.GradeCurricularDto;
import com.br.fatec.AGIS.model.Curso;
import com.br.fatec.AGIS.model.GradeCurricular;
import com.br.fatec.AGIS.repository.CursoRepository;
import com.br.fatec.AGIS.repository.GradeCurricularRepository;

@Service
public class GradeCurricularService {
	@Autowired
	private GradeCurricularRepository gradeCurricularRepository;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	public List<GradeCurricular> selectAll() {
		return gradeCurricularRepository.findAll();
	}
	
	public GradeCurricular selectById(Long cod) throws Exception {
		Optional<GradeCurricular> gradeCurricular = gradeCurricularRepository.findById(cod);
		
		if (gradeCurricular.isEmpty()) {
			throw new Exception("Disciplina não registrada");
		}
		
		return gradeCurricular.get();
	}
	
	public GradeCurricular insert(GradeCurricularDto gradeCurricularDto) {
		Optional<Curso> curso = cursoRepository.findById(gradeCurricularDto.codCurso());
		var gradeCurricularModel = new GradeCurricular(gradeCurricularDto, curso.get());

		return gradeCurricularRepository.save(gradeCurricularModel);
	}
}
