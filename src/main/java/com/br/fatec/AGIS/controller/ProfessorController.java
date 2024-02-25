package com.br.fatec.AGIS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.br.fatec.AGIS.model.Professor;
import com.br.fatec.AGIS.service.ProfessorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/professor")
@CrossOrigin("http://localhost:3000")
public class ProfessorController {
	@Autowired
	private ProfessorService professorService;

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

	@PostMapping
	public ResponseEntity<Professor> insert(@RequestBody @Valid ProfessorDto professorDto) {
		return ResponseEntity.status(HttpStatus.OK).body(professorService.insert(professorDto));
	}

	// LOGIN
	@PostMapping("/login")
	public ResponseEntity<Professor> login(@RequestBody LoginDto loginDto) {
		System.out.println(loginDto.toString());
		return ResponseEntity.status(HttpStatus.OK).body(professorService.login(loginDto.cpf()));
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