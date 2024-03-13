package com.br.fatec.AGIS.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.fatec.AGIS.dto.LoginDto;
import com.br.fatec.AGIS.dto.SecretarioDto;
import com.br.fatec.AGIS.dto.TokenDto;
import com.br.fatec.AGIS.model.Secretario;
import com.br.fatec.AGIS.service.SecretarioService;
import com.br.fatec.AGIS.service.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/secretario")
public class SecretarioController {
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private SecretarioService secretarioService;
	
	@Autowired
	private TokenService tokenService;
	
	@PostMapping("/login")
	public ResponseEntity<TokenDto> login(@RequestBody @Valid LoginDto loginDto) {
		var userPassowrd = new UsernamePasswordAuthenticationToken(loginDto.cpf(), loginDto.senha());
		var auth = authenticationManager.authenticate(userPassowrd);
		
		var token = tokenService.generateToken((Secretario) auth.getPrincipal());
		
		return ResponseEntity.status(HttpStatus.OK).body(new TokenDto(token));
	}
	
	@PostMapping("/registro")
	public ResponseEntity<Secretario> insert(@RequestBody @Valid SecretarioDto secretarioDto) {
		if (secretarioService.loadUserByUsername(secretarioDto.cpf()) == null) { 
			return ResponseEntity.status(HttpStatus.OK).body(secretarioService.insert(secretarioDto));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
}
