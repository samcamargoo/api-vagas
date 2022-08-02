package com.sam.vagas.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.sam.vagas.dto.VagaRequestDto;
import com.sam.vagas.dto.VagaResponseDto;
import com.sam.vagas.entities.Usuario;
import com.sam.vagas.entities.Vaga;
import com.sam.vagas.repositories.UsuarioRepository;
import com.sam.vagas.repositories.VagaRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VagaServiceImpl implements VagaService {

	private final UsuarioRepository usuarioRepository;
	private final VagaRepository vagaRepository;
	private final UsuarioService usuarioService;

	@Override
	@Transactional
	public ResponseEntity<Object> cadastrarVaga(VagaRequestDto vagaRequestDto) {

		// recupera o usuario logado
		UserDetails usuario = usuarioService.getCurrentUser();

		// procura usuario no banco de dados
		Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(usuario.getUsername());

		List<Vaga> listaDeVagas = vagaRepository.findAllVagasById(usuarioOptional.get().getId());

		if (usuarioOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso negado");
		}

		if (!usuarioOptional.get().isPremium() && listaDeVagas.size() > 1) {
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("Você excedeu o máximo de vagas cadastradas para usuários gratuitos");
		}
		var vaga = new Vaga();
		BeanUtils.copyProperties(vagaRequestDto, vaga);

		// relaciona a vaga a ser cadastrada com o usuario logado
		vaga.setUsuario(usuarioOptional.get());
		vagaRepository.save(vaga);

		vagaRequestDto.setId(vaga.getId());

		return ResponseEntity.status(HttpStatus.CREATED).body(vagaRequestDto);
	}

	@Override
	public ResponseEntity<Object> listarVagas() {
		// recupera o usuario logado
		UserDetails usuario = usuarioService.getCurrentUser();

		// procura o usuario no banco de dados de acordo com o email do usuario logado
		Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(usuario.getUsername());

		List<Vaga> listaDeVagas = vagaRepository.findAllVagasById(usuarioOptional.get().getId());
		List<VagaResponseDto> listaDto = listaDeVagas.stream().map(x -> new VagaResponseDto(x))
				.collect(Collectors.toList());
		return ResponseEntity.status(HttpStatus.OK).body(listaDto);
	}

	@Override
	@Transactional
	public ResponseEntity<Object> deletarVagaPorId(UUID id) {

		// recupera o usuario logado
		UserDetails usuario = usuarioService.getCurrentUser();

		// procura o usuario no banco de dados de acordo com o email do usuario logado
		Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(usuario.getUsername());

		Optional<Vaga> vagaOptional = vagaRepository.findById(id);

		// verifica se a vaga a ser deletada não pertence ao usuario logado
		if (vagaOptional.get().getUsuario().getId() != usuarioOptional.get().getId()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso Negado");
		}

		if (vagaOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Vaga não existe");
		}

		vagaRepository.deleteById(id);
		return ResponseEntity.status(HttpStatus.OK).body("Vaga deletada com sucesso");
	}

	@Override
	public ResponseEntity<Object> listarVagaPorId(UUID id) {

		// recupera o usuario logado
		UserDetails usuario = usuarioService.getCurrentUser();
		// procura o usuario no banco de dados de acordo com o email do usuario logado
		Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(usuario.getUsername());

		Optional<Vaga> vagaOptional = vagaRepository.findById(id);

		// verifica se a vaga a ser listada não pertence ao usuario logado
		if (vagaOptional.get().getUsuario().getId() != usuarioOptional.get().getId()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso Negado");
		}

		if (vagaOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga não existe");
		}

		var vagaResponse = new VagaResponseDto();
		BeanUtils.copyProperties(vagaOptional.get(), vagaResponse);
		return ResponseEntity.status(HttpStatus.OK).body(vagaResponse);
	}

	@Override
	public ResponseEntity<Object> editarVagaPorId(UUID id, VagaRequestDto vagaRequestDto) {

		// recupera o usuario logado
		UserDetails usuario = usuarioService.getCurrentUser();

		Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(usuario.getUsername());

		Optional<Vaga> vagaOptional = vagaRepository.findById(id);

		// verifica se a vaga a ser editada não pertence ao usuario logado
		if (vagaOptional.get().getUsuario().getId() != usuarioOptional.get().getId()) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acesso Negado");
		}

		if (vagaOptional.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vaga não existe");
		}

		var vaga = new Vaga();
		BeanUtils.copyProperties(vagaRequestDto, vaga);
		vaga.setId(vagaOptional.get().getId());
		vaga.setUsuario(vagaOptional.get().getUsuario());
		vagaRepository.save(vaga);

		var vagaResponseDto = new VagaResponseDto(vaga);
		return ResponseEntity.status(HttpStatus.OK).body(vagaResponseDto);

	}

}
