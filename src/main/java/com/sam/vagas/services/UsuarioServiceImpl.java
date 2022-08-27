package com.sam.vagas.services;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sam.vagas.dto.UsuarioResponseDto;
import com.sam.vagas.entities.Usuario;
import com.sam.vagas.enums.RolesEnum;
import com.sam.vagas.repositories.RoleRepository;
import com.sam.vagas.repositories.UsuarioRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

	private final RoleRepository roleRepository;
	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public ResponseEntity<Object> cadastrarUsuario(Usuario usuario) {

		if (existsByEmail(usuario.getEmail())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body("Email em uso");
		}

		usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
		usuario.setRoles(Arrays.asList(roleRepository.findRole(RolesEnum.ROLE_USER)));
		usuario.setPremium(false);
		usuarioRepository.save(usuario);

		var usuarioDto = new UsuarioResponseDto(usuario.getNome(), usuario.getSobrenome(), usuario.getEmail());

		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioDto);

	}

	@Override
	public boolean existsByEmail(String email) {

		if (usuarioRepository.existsByEmailIgnoreCase(email)) {
			return true;
		}
		return false;
	}

	@Override
	public UserDetails getCurrentUser() {

		UserDetails user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		return user;
	}

	@Override
	public String getUsuarioNome(String email) {
		
		Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(email);
		String nome = usuarioOptional.get().getNome();
		return nome;
	}


}
