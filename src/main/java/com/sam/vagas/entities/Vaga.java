package com.sam.vagas.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.sam.vagas.enums.ModeloTrabalhoEnum;
import com.sam.vagas.enums.VagasEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vaga implements Serializable {


	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue (strategy = GenerationType.AUTO)
	private UUID id;
	@Column(nullable = false)
	private String cargo;
	@Column(nullable = false)
	private String descricao;
	@Enumerated(EnumType.STRING)
	private VagasEnum senioridade;
	
	private LocalDateTime adicionadaEm;

	@Enumerated(EnumType.STRING)
	private ModeloTrabalhoEnum modelo;
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
}
