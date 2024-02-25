package com.br.fatec.AGIS.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.fatec.AGIS.dto.DataDto;
import com.br.fatec.AGIS.model.Data;
import com.br.fatec.AGIS.service.DataService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/datas")
public class DataController {
	@Autowired
	private DataService dataService;
	
	@GetMapping
	public ResponseEntity<List<Data>> getAll() {
		return ResponseEntity.status(HttpStatus.OK).body(dataService.selectAll());
	}

	@GetMapping("/{data}")
	public ResponseEntity<Object> getId(@PathVariable("data") LocalDate data) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(dataService.selectById(data));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<Data> insert(@RequestBody @Valid DataDto dataDto) {
		return ResponseEntity.status(HttpStatus.OK).body(dataService.insert(dataDto));
	}

	@PutMapping("/{data}")
	public ResponseEntity<Object> update(@PathVariable("data") LocalDate data, @RequestBody @Valid DataDto dataDto) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(dataService.update(data, dataDto));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@DeleteMapping("/{data}")
	public ResponseEntity<Object> delete(@PathVariable("data") LocalDate data) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(dataService.delete(data));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}
}
