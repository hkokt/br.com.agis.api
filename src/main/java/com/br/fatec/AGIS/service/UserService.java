package com.br.fatec.AGIS.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.br.fatec.AGIS.repository.ProfessorRepository;
import com.br.fatec.AGIS.repository.SecretarioRepository;

@Service
public class UserService implements UserDetailsService {
	@Autowired
	private SecretarioRepository secretarioRepository;
	
	@Autowired
	private ProfessorRepository professorRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		if (secretarioRepository.findByCpf(username) != null) {
			return secretarioRepository.findByCpf(username);			
		}
		
		if (professorRepository.findByCpf(username) != null) {
			return professorRepository.findByCpf(username);			
		}
		
		return null;
	}

}
