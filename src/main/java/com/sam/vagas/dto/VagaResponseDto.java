package com.sam.vagas.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sam.vagas.entities.Vaga;
import com.sam.vagas.enums.ModeloTrabalhoEnum;
import com.sam.vagas.enums.VagasEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VagaResponseDto {

	private UUID id;
	private String cargo;
	private String descricao;
	@Enumerated(EnumType.STRING)
	private VagasEnum senioridade;
	private ModeloTrabalhoEnum modelo;
	@JsonFormat(pattern = "dd/MM/yyyy HH:ss:mm")
	private LocalDateTime adicionadaEm;
	
	public VagaResponseDto(Vaga vaga) {
		this.id = vaga.getId();
		this.cargo = vaga.getCargo();
		this.descricao = vaga.getDescricao();
		this.senioridade = vaga.getSenioridade();
		this.modelo = vaga.getModelo();
		this.adicionadaEm = vaga.getAdicionadaEm();
	}
}
