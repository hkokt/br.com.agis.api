package com.br.fatec.AGIS.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.fatec.AGIS.dto.ChamadaDto;
import com.br.fatec.AGIS.model.Aluno;
import com.br.fatec.AGIS.model.Chamada;
import com.br.fatec.AGIS.model.Turma;
import com.br.fatec.AGIS.repository.AlunoRepository;
import com.br.fatec.AGIS.repository.ChamadaRepository;
import com.br.fatec.AGIS.repository.TurmaRepository;

@Service
public class ChamadaService {
	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private ChamadaRepository chamadaRepository;
	
	@Autowired
	private TurmaRepository turmaRepository;
	
	public List<Chamada> selectAllByDataAndCodTurma(Long codTurma, String data) {
		return chamadaRepository.selectAllByDataAndCodTurma(codTurma, LocalDate.parse(data));
	}
	
	public Chamada insert(ChamadaDto chamadaDto) {
		Optional<Turma> turma = turmaRepository.findById(chamadaDto.codTurma());
		Optional<Aluno> aluno = alunoRepository.findById(chamadaDto.ra());
		
		var chamadaModel = new Chamada(chamadaDto, turma.get(), aluno.get());
		
		return chamadaRepository.save(chamadaModel);
	}
}
