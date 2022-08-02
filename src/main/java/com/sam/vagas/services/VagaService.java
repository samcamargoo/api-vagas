package com.sam.vagas.services;

import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.sam.vagas.dto.VagaRequestDto;

public interface VagaService {

	ResponseEntity<Object> cadastrarVaga(VagaRequestDto vagaRequestDto);
	ResponseEntity<Object> listarVagas();
	ResponseEntity<Object> deletarVagaPorId(UUID id);
	ResponseEntity<Object> listarVagaPorId(UUID id);
	ResponseEntity<Object> editarVagaPorId(UUID id, VagaRequestDto vagaRequestDto);
}
