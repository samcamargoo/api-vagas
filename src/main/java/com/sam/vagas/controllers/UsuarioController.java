package com.sam.vagas.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sam.vagas.entities.Usuario;
import com.sam.vagas.services.UsuarioService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/v1/usuarios")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class UsuarioController {

	final UsuarioService usuarioService;
	
	
	@PostMapping("/cadastro")
	public ResponseEntity<Object> cadastrarUsuario(@RequestBody Usuario usuario) {
		return usuarioService.cadastrarUsuario(usuario);
	}
	
	@GetMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String get() {
		return "hello";
	}
	
	@GetMapping("/usuario-normal")
	@PreAuthorize("hasRole('ROLE_USER')")
	public String usuario() {
		return "voce é usuario";
	}
	
	
}
