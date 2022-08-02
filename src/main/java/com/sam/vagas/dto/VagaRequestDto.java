package com.sam.vagas.dto;

import java.util.UUID;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

import com.sam.vagas.entities.Vaga;
import com.sam.vagas.enums.VagasEnum;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VagaRequestDto {


	private UUID id;
	@NotBlank
	private String titulo;
	@NotBlank
	private String descricao;
	@Enumerated(EnumType.STRING)
	private VagasEnum tipo;
	
	public VagaRequestDto(Vaga vaga) {
		this.id = vaga.getId();
		this.titulo = vaga.getTitulo();
		this.tipo = vaga.getTipo();
	}
}
