package com.sam.vagas.services;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import com.sam.vagas.entities.Usuario;

public interface UsuarioService {

	ResponseEntity<Object> cadastrarUsuario(Usuario usuario);
	
	boolean existsByEmail(String email);
	UserDetails getCurrentUser();
}
