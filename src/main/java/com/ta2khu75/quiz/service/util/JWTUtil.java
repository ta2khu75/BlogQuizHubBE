package com.ta2khu75.quiz.service.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.ta2khu75.quiz.configuration.SecurityJwtConfig;
import com.ta2khu75.quiz.mapper.AccountMapper;
import com.ta2khu75.quiz.model.entity.Account;
import com.ta2khu75.quiz.model.response.AccountResponse;
import com.ta2khu75.quiz.model.response.TokenResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JWTUtil {
	@Value("${jwt.expiration}")
	private long expiration;
	@Value("${jwt.refresh.expiration}")
	private long refreshExpiration;
	private final JwtEncoder jwtEncoder;
	private final JwtDecoder jwtDecoder;

	public TokenResponse createToken(AccountResponse response) {
		Instant now = Instant.now();
		Instant validity = now.plus(this.expiration, ChronoUnit.SECONDS);
		JwtClaimsSet claims = JwtClaimsSet.builder().issuer("com.ta2khu75").issuedAt(now).expiresAt(validity)
				.subject(response.getInfo().getId())
				.claim("profile", response.getProfile())
				.claim("scope", "ROLE_" + response.getStatus().getRole().getName()).build();
		JwsHeader jwsHeader = JwsHeader.with(SecurityJwtConfig.JWT_ALGORITHM).build();
		String token = jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
		return new TokenResponse(token, validity.toEpochMilli());
	}

	public TokenResponse createRefreshToken(Account response) {
		Instant now = Instant.now();
		Instant validity = now.plus(this.refreshExpiration, ChronoUnit.SECONDS);
		JwtClaimsSet claims = JwtClaimsSet.builder().issuer("com.ta2khu75").issuedAt(now).expiresAt(validity)
				.subject(response.getId()).build();
		JwsHeader jwsHeader = JwsHeader.with(SecurityJwtConfig.JWT_ALGORITHM).build();
		String token=jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
		return new TokenResponse(token, validity.toEpochMilli());
	}

	public Jwt validateToken(String token) {
		return jwtDecoder.decode(token);
	}
}
