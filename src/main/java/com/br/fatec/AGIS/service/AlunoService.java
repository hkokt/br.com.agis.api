package com.br.fatec.AGIS.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.br.fatec.AGIS.dto.AlunoDto;
import com.br.fatec.AGIS.model.Aluno;
import com.br.fatec.AGIS.model.Curso;
import com.br.fatec.AGIS.model.Usuario;
import com.br.fatec.AGIS.repository.AlunoRepository;
import com.br.fatec.AGIS.repository.CursoRepository;

@Service
public class AlunoService {
	@Autowired
	private AlunoRepository alunoRepository;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	public List<Aluno> selectAll() {
		return alunoRepository.findAll();
	}
	
	public Aluno selectId(String ra) throws Exception {
		Optional<Aluno> aluno = alunoRepository.findById(ra);
		if (aluno.isEmpty()) {
			throw new Exception("Curso não registrado");
		}

		return aluno.get();
	}

	public Aluno insert(AlunoDto alunoDto) {
		var alunoModel = new Aluno();
		Usuario user = new Usuario();
		
		Optional<Curso> curso = cursoRepository.findById(alunoDto.codCurso());
		
		user.setCpf(alunoDto.cpf());
		user.setNome(alunoDto.nome());
		user.setDataNasc(alunoDto.dataNasc());
		user.setEmailPessoal(alunoDto.emailPessoal());
		user.setEmailCorp(geraEmailCorp(alunoDto.nome().trim()));
		user.setSituacao("ativo");
		user.setSenha("123456");
		alunoModel.setUsuario(user);
		
		alunoModel.setNomeSocial(alunoDto.nomeSocial());
		alunoModel.setDataConc2grau(alunoDto.dataConc2grau());
		alunoModel.setInstConc2grau(alunoDto.instConc2grau());
		alunoModel.setPtVestibular(alunoDto.ptVestibular());
		alunoModel.setPosVestibular(alunoDto.posVestibular());
		
		alunoModel.setRa(geraRa());
		alunoModel.setDataMatricula(LocalDate.now());
		alunoModel.setDataLimiteMatricula(calcDataLimite());
		
		alunoModel.setCurso(curso.get());

		return alunoRepository.save(alunoModel);
	}
	
	public Aluno delete(String ra) throws Exception {
		Optional<Aluno> aluno = alunoRepository.findById(ra);
		
		if (aluno.isEmpty()) {
			throw new Exception("Aluno não registrada");
		}
		
		var alunoModel = aluno.get();
		alunoModel.setCurso(null);
		
		alunoRepository.delete(alunoModel);
		
		return alunoModel;
	}
	
	public Aluno trancar(String ra) {
		Optional<Aluno> aluno = alunoRepository.findById(ra);
		
		var alunoModel = aluno.get();
		alunoModel.getUsuario().setSituacao("Trancado");
		
		return alunoRepository.save(alunoModel);
	}
	
	private String geraRa() {
		String ra = "";
		LocalDate data = LocalDate.now();
		
		ra += data.getYear();
		
		if (data.getMonthValue() > 6) { ra += 2; } else { ra += 1; }
		
		for (int i = 0; i < 4; i++) {
			int num = (int) (Math.random() * 10) + 1;
			ra += num;
		}
		
		return ra;
	}
	
	private String geraEmailCorp(String nome) {
		String email = nome;
		
		for (int i = 0; i < 4; i++) {
			email += (int) (Math.random() * 10);
		}	
		
		email += "@agis.com";
		
		return email.trim();
	}
	
	private LocalDate calcDataLimite() {
		return LocalDate.now().plusYears(5);
	}
}
