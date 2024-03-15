package com.br.fatec.AGIS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.fatec.AGIS.dto.LoginDto;
import com.br.fatec.AGIS.dto.ProfessorDto;
import com.br.fatec.AGIS.dto.TokenDto;
import com.br.fatec.AGIS.model.Professor;
import com.br.fatec.AGIS.service.ProfessorService;
import com.br.fatec.AGIS.service.TokenService;
import com.br.fatec.AGIS.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/professores")
public class ProfessorController {
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private ProfessorService professorService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TokenService tokenService;
	
	@GetMapping
	public ResponseEntity<List<Professor>> getAll() {
		return ResponseEntity.status(HttpStatus.OK).body(professorService.selectAll());
	}

	@GetMapping("/{cod}")
	public ResponseEntity<Object> getId(@PathVariable("cod") Long cod) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(professorService.selectById(cod));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@PostMapping("/login")
	public ResponseEntity<TokenDto> login(@RequestBody @Valid LoginDto loginDto) {
		var userPassowrd = new UsernamePasswordAuthenticationToken(loginDto.cpf(), loginDto.senha());
		var auth = authenticationManager.authenticate(userPassowrd);
		
		Professor prof = (Professor) auth.getPrincipal();
		
		var token = tokenService.generateToken(prof.getCpf());
		
		return ResponseEntity.status(HttpStatus.OK).body(new TokenDto(token));
	}
	
	@PostMapping("/registro")
	public ResponseEntity<Professor> insert(@RequestBody @Valid ProfessorDto professorDto) {
		if (userService.loadUserByUsername(professorDto.cpf()) == null) {
			return ResponseEntity.status(HttpStatus.OK).body(professorService.insert(professorDto));
		} else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}

	@PutMapping("/{cod}")
	public ResponseEntity<Object> update(@PathVariable("cod") Long cod, @RequestBody @Valid ProfessorDto professorDto) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(professorService.update(cod, professorDto));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@DeleteMapping("/{cod}")
	public ResponseEntity<Object> delete(@PathVariable("cod") Long cod) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(professorService.delete(cod));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
}