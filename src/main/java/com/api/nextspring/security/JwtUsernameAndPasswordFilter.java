package com.api.nextspring.security;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;

@RequiredArgsConstructor
public class JwtUsernameAndPasswordFilter extends UsernamePasswordAuthenticationFilter {
	private final JwtTokenProvider jwtTokenProvider;

	@Override
	protected void successfulAuthentication(
			@NonNull HttpServletRequest request,
			@NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain,
			@NonNull Authentication authenticationResult
	) throws ServletException, IOException {
		String token = Jwts
				.builder()
				.setSubject(authenticationResult.getName())
				.claim("authorities", authenticationResult.getAuthorities())
				.setIssuedAt(Date.valueOf(LocalDate.now()))
				.setExpiration(Date.valueOf(LocalDate.now().plusWeeks(2)))
				.signWith(jwtTokenProvider.key())
				.compact();

		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("Access-Control-Expose-Headers", "Authorization");
		response.addCookie(new Cookie("Authorization", "Bearer " + token));

		super.successfulAuthentication(request, response, filterChain, authenticationResult);
	}
}
