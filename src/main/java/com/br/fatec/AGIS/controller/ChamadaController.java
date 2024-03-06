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

import com.br.fatec.AGIS.dto.ChamadaDto;
import com.br.fatec.AGIS.model.Chamada;
import com.br.fatec.AGIS.service.ChamadaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/chamadas")
public class ChamadaController {
	@Autowired
	private ChamadaService chamadaService;
	
	@GetMapping("/{cod}/{data}")
	public ResponseEntity<List<Chamada>> getAllByDataAndCodTurma(@PathVariable("cod") Long codTurma, @PathVariable("data") String data) {
		return ResponseEntity.status(HttpStatus.OK).body(chamadaService.selectAllByDataAndCodTurma(codTurma, data));
	}
	
	@GetMapping("/verificacao/{cod}/{data}")
	public ResponseEntity<Boolean> getAllByDataAndCodTurmaVerficacao(@PathVariable("cod") Long codTurma, @PathVariable("data") String data) {
		if (chamadaService.selectAllByDataAndCodTurma(codTurma, data).isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(Boolean.TRUE);
		} else {
			return ResponseEntity.status(HttpStatus.OK).body(Boolean.FALSE);
		}
	}
	
	@PostMapping
	public ResponseEntity<Chamada> insert(@Valid @RequestBody ChamadaDto chamadaDto) {
		return ResponseEntity.status(HttpStatus.OK).body(chamadaService.insert(chamadaDto));
	}
}
