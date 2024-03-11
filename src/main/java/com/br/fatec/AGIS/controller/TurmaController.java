package com.br.fatec.AGIS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.fatec.AGIS.dto.TurmaDto;
import com.br.fatec.AGIS.model.Turma;
import com.br.fatec.AGIS.service.TurmaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/turmas")
public class TurmaController {
	@Autowired
	private TurmaService turmaService;
	
	@GetMapping("/grade/{cod}")
	public ResponseEntity<List<Turma>> getAllFromGrade(@PathVariable("cod") Long codGrade) {
		return ResponseEntity.status(HttpStatus.OK).body(turmaService.selectAllFromGrade(codGrade));
	}
	
	@GetMapping("/professor/{cod}")
	public ResponseEntity<List<Turma>> getAllFromProf(@PathVariable("cod") Long codProf) {
		return ResponseEntity.status(HttpStatus.OK).body(turmaService.selectAllFromProf(codProf));
	}
	
	@GetMapping("/{cod}")
	public ResponseEntity<Object> getId(@PathVariable("cod") Long cod) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(turmaService.selectById(cod));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@PostMapping
	public ResponseEntity<Turma> insert(@RequestBody @Valid TurmaDto turmaDto) {
		return ResponseEntity.status(HttpStatus.OK).body(turmaService.insert(turmaDto));
	}
	
	@PutMapping("/{cod}")
	public ResponseEntity<Object> update(@PathVariable("cod") Long cod, @RequestBody @Valid TurmaDto turmaDto) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(turmaService.update(cod, turmaDto));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@DeleteMapping("/{cod}")
	public ResponseEntity<Object> delete(@PathVariable("cod") Long cod) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(turmaService.delete(cod));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
	
	@PatchMapping("/{cod}/{metodo}")
	public ResponseEntity<Turma> insertMetodoAvalitivo(@PathVariable("cod") Long cod, @PathVariable("metodo") String metodo) {
		return ResponseEntity.status(HttpStatus.OK).body(turmaService.insertMetodoAvalitivo(cod, metodo));
	}
}
