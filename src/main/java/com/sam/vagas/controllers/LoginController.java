package com.sam.vagas.controllers;

import org.springframework.security.core.AuthenticationException;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sam.vagas.configs.security.JWTTokenService;
import com.sam.vagas.dto.TokenDto;
import com.sam.vagas.entities.LoginForm;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("api/v1/login")
@AllArgsConstructor
public class LoginController {

	private final AuthenticationManager authManager;
	private final JWTTokenService tokenService;
	@PostMapping
	public ResponseEntity<Object> autenticar(@RequestBody @Valid LoginForm form) {
		
		UsernamePasswordAuthenticationToken login = form.converter();
		
		try {
			final Authentication authentication = authManager.authenticate(login);
			
			String token = tokenService.gerarToken(authentication);
			return ResponseEntity.status(HttpStatus.OK).body(new TokenDto(token, "Bearer"));
		} catch(AuthenticationException e) {
			return ResponseEntity.notFound().build();
		}
	}
}
