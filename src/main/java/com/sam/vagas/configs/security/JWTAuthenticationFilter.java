package com.sam.vagas.configs.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.sam.vagas.entities.Usuario;
import com.sam.vagas.repositories.UsuarioRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	private final JWTTokenService jwtTokenService;
	private final UsuarioRepository usuarioRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String token = recuperaToken(request);
		boolean valido = jwtTokenService.isTokenValido(token);

		if (valido) {
			autenticarUsuario(token);

		}

		filterChain.doFilter(request, response);
	}

	private void autenticarUsuario(String token) {
		final String emailUsuario = jwtTokenService.getIdUsuario(token);
		final Usuario usuario = usuarioRepository.findByEmail(emailUsuario).get();
		final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario,
				null, usuario.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
	}

	private String recuperaToken(HttpServletRequest request) {
		final String token = request.getHeader("Authorization");
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		return token.substring(7, token.length());
	}

}
