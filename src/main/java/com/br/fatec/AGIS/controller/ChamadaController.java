package com.br.fatec.AGIS.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@GetMapping("/datas")
	public ResponseEntity<List<Chamada>> getAllByData(@RequestParam("data") LocalDate data, @RequestParam("codTurma") Long codTurma) {
		return ResponseEntity.status(HttpStatus.OK).body(chamadaService.selectAllByData(data, codTurma));
	}
	
	@PostMapping
	public ResponseEntity<Chamada> insert(@Valid @RequestBody ChamadaDto chamadaDto) {
		return ResponseEntity.status(HttpStatus.OK).body(chamadaService.insert(chamadaDto));
	}
}
