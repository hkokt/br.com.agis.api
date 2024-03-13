package com.br.fatec.AGIS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.br.fatec.AGIS.dto.SecretarioDto;
import com.br.fatec.AGIS.model.Secretario;
import com.br.fatec.AGIS.repository.SecretarioRepository;

@Service
public class SecretarioService implements UserDetailsService {
	
	@Autowired
	private SecretarioRepository secretarioRepository;
	
	public Secretario insert(SecretarioDto secretarioDto) {
		String encryptedPasswd = new BCryptPasswordEncoder().encode(secretarioDto.senha());
		var secretario = new Secretario();
		
		secretario.setCpf(secretarioDto.cpf());
		secretario.setSenha(encryptedPasswd);
		secretario.setRole(secretarioDto.role());
		secretario.setNome(secretarioDto.nome());
		secretario.setDataNasc(secretarioDto.dataNasc());
		secretario.setEmailPessoal(secretarioDto.emailPessoal());
		secretario.setEmailCorp(geraEmailCorp(secretarioDto.nome()));
		secretario.setSituacao("ativo");
		
		return secretarioRepository.save(secretario);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return secretarioRepository.findByCpf(username);
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
