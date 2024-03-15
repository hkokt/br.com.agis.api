package com.br.fatec.AGIS.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.fatec.AGIS.dto.ProfessorDto;
import com.br.fatec.AGIS.model.Professor;
import com.br.fatec.AGIS.repository.ProfessorRepository;

@Service
public class ProfessorService {
	@Autowired
	private ProfessorRepository professorRepository;
	
	public List<Professor> selectAll() {
		return professorRepository.findAll();
	}
	
	public Professor selectById(Long id) throws Exception {
		Optional<Professor> professor = professorRepository.findById(id);
		if (professor.isEmpty()) {
			throw new Exception("Curso não registrado");
		}

		return professor.get();
	}
	
	public Professor insert(ProfessorDto professorDto) {
		String encryptedPasswd = new BCryptPasswordEncoder().encode("123456");
		var professorModel = new Professor();
		
		professorModel.setCpf(professorDto.cpf());
		professorModel.setSenha(encryptedPasswd);
		professorModel.setRole(professorDto.role());
		professorModel.setTitulacao(professorDto.titulacao());
		professorModel.setNome(professorDto.nome());
		professorModel.setDataNasc(professorDto.dataNasc());
		professorModel.setEmailPessoal(professorDto.emailPessoal());
		professorModel.setEmailCorp(geraEmailCorp(professorDto.nome()));
		professorModel.setSituacao("ativo");
		
		return professorRepository.save(professorModel);
	}
	
	public Professor update(Long cod, ProfessorDto professorDto) throws Exception {
		Optional<Professor> professor = professorRepository.findById(cod);
		
		var professorModel = professor.get();
		
		if (professor.isEmpty()) {
			throw new Exception("Professor não registrado");
		}
		
		professorModel.setCpf(professorDto.cpf());
		professorModel.setNome(professorDto.nome());
		professorModel.setDataNasc(professorDto.dataNasc());
		professorModel.setEmailPessoal(professorDto.emailPessoal());
		professorModel.setTitulacao(professorDto.titulacao());
		
		return professorRepository.save(professorModel);
	}
	
	public Professor delete(Long id) throws Exception {
		Optional<Professor> professor = professorRepository.findById(id);
		
		if (professor.isEmpty()) {
			throw new Exception("Professor não registrado");
		}

		var professorModel = professor.get();
		professorRepository.delete(professorModel);

		return professorModel;
	}
	
	private String geraEmailCorp(String nome) {
		String email = nome.trim();
		
		for (int i = 0; i < 4; i++) {
			email += (int) (Math.random() * 10);
		}	
		
		email += "@agis.com";
		
		return email.trim();
	}
}