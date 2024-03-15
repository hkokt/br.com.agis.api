package com.br.fatec.AGIS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.fatec.AGIS.dto.SecretarioDto;
import com.br.fatec.AGIS.model.Secretario;
import com.br.fatec.AGIS.repository.SecretarioRepository;

@Service
public class SecretarioService  {
	
	@Autowired
	private SecretarioRepository secretarioRepository;
	
	public Secretario insert(SecretarioDto secretarioDto) {
		String encryptedPasswd = new BCryptPasswordEncoder().encode(secretarioDto.senha());
		var secretarioModel = new Secretario();
		
		secretarioModel.setCpf(secretarioDto.cpf());
		secretarioModel.setSenha(encryptedPasswd);
		secretarioModel.setRole(secretarioDto.role());
		secretarioModel.setNome(secretarioDto.nome());
		secretarioModel.setDataNasc(secretarioDto.dataNasc());
		secretarioModel.setEmailPessoal(secretarioDto.emailPessoal());
		secretarioModel.setEmailCorp(geraEmailCorp(secretarioDto.nome()));
		secretarioModel.setSituacao("ativo");
		
		return secretarioRepository.save(secretarioModel);
	}
	
	private String geraEmailCorp(String nome) {
		String email = nome;
		
		for (int i = 0; i < 4; i++) {
			email += (int) (Math.random() * 10);
		}	
		
		email += "@agis.com";
		
		return email.trim();
	}

}
