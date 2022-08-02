package com.sam.vagas.configs.security;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTTokenService {

	@Value("${jwt.expiracao}")
	private Long expiracao;

	SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
	
	/**
	 * @param authentication
	 * @return
	 */
	public String gerarToken(Authentication authentication) {
		return Jwts.builder()
					.setIssuer("Api vagas")
					.setIssuedAt(new Date())
					.setExpiration(new Date(System.currentTimeMillis() + expiracao))
					.signWith(key).compact();
					
	}
	
	public boolean isTokenValido(String token) {
		try {
			getInformacoesDoToken(token);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	public Long getIdUsuario(String token) {
		return Long.parseLong(getClaimFromToken(token, Claims::getSubject));
	}
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getInformacoesDoToken(token);
		return claimsResolver.apply(claims);
	}
	
	private Claims getInformacoesDoToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
}
