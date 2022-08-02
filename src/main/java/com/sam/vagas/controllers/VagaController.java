package com.sam.vagas.controllers;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sam.vagas.dto.VagaRequestDto;
import com.sam.vagas.services.VagaService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/v1/vagas")
@AllArgsConstructor
public class VagaController {

	private final VagaService vagaService;
	
	@PostMapping
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Object> cadastrarVaga(@RequestBody @Valid VagaRequestDto vagaRequestDto) {
		return vagaService.cadastrarVaga(vagaRequestDto);
	}
	
	
	@GetMapping
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Object> listarVagas() {
		return vagaService.listarVagas();
	}
	
	@GetMapping("{id}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Object> listarVagaPorId(@PathVariable(name = "id") UUID id) {
		return vagaService.listarVagaPorId(id);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Object> deletarVagaPorId(@PathVariable(name = "id") UUID id) {
		return vagaService.deletarVagaPorId(id);
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("hasRole('ROLE_USER')")
	public ResponseEntity<Object> editarVagaPorId(@PathVariable (name = "id") UUID id, @RequestBody VagaRequestDto vagaRequestDto) {
		return vagaService.editarVagaPorId(id, vagaRequestDto);
	}
}
