package com.sam.vagas.dto;

import java.util.UUID;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.sam.vagas.entities.Vaga;
import com.sam.vagas.enums.VagasEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VagaResponseDto {

	private UUID id;
	private String titulo;
	private String descricao;
	@Enumerated(EnumType.STRING)
	private VagasEnum tipo;
	
	public VagaResponseDto(Vaga vaga) {
		this.id = vaga.getId();
		this.titulo = vaga.getTitulo();
		this.tipo = vaga.getTipo();
		this.descricao = vaga.getDescricao();
	}
}
