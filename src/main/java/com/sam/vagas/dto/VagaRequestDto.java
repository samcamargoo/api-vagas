package com.sam.vagas.dto;

import java.util.UUID;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

import com.sam.vagas.entities.Vaga;
import com.sam.vagas.enums.ModeloTrabalhoEnum;
import com.sam.vagas.enums.VagasEnum;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VagaRequestDto {


	private UUID id;
	@NotBlank
	private String cargo;
	@NotBlank
	private String descricao;
	@Enumerated(EnumType.STRING)
	private VagasEnum senioridade;
	
	
	@Enumerated(EnumType.STRING)
	private ModeloTrabalhoEnum modelo;
	
	public VagaRequestDto(Vaga vaga) {
		this.id = vaga.getId();
		this.cargo = vaga.getCargo();
		this.descricao = vaga.getDescricao();
		this.senioridade = vaga.getSenioridade();
		this.modelo = vaga.getModelo();
	}
}
