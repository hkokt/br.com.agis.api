package com.br.fatec.AGIS.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.fatec.AGIS.dto.GradeCurricularDto;
import com.br.fatec.AGIS.model.GradeCurricular;
import com.br.fatec.AGIS.service.GradeCurricularService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/gradeCurricular")
public class GradeCurricularController {
	@Autowired
	private GradeCurricularService gradeCurricularService;
	
	@GetMapping
	public ResponseEntity<List<GradeCurricular>> getAll() {
		return ResponseEntity.status(HttpStatus.OK).body(gradeCurricularService.selectAll());
	}
	
	@GetMapping("/{cod}")
	public ResponseEntity<Object> getId(@PathVariable("cod") Long cod) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(gradeCurricularService.selectById(cod));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<GradeCurricular> insert(@RequestBody @Valid GradeCurricularDto gradeCurricularDto) {
		return ResponseEntity.status(HttpStatus.OK).body(gradeCurricularService.insert(gradeCurricularDto));
	}
}
