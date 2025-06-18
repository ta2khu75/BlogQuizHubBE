package com.ta2khu75.quiz.service.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.ta2khu75.quiz.configuration.JwtProperties;
import com.ta2khu75.quiz.configuration.JwtProviderFactory;
import com.ta2khu75.quiz.exception.UnAuthenticatedException;
import com.ta2khu75.quiz.model.TokenConfig;
import com.ta2khu75.quiz.model.TokenType;
import com.ta2khu75.quiz.model.response.TokenResponse;
import com.ta2khu75.quiz.model.response.account.AccountResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JWTUtil {
	private final JwtProviderFactory jwtProviderFactory;
	private final JwtProperties jwtProperties;

	public String createAccessToken(AccountResponse response) {
		Instant now = Instant.now();
		TokenConfig tokenConfig=jwtProperties.getConfigByType(TokenType.ACCESS);
		JwtEncoder jwtEncoder = jwtProviderFactory.getEncoder(TokenType.ACCESS);
		Instant validity = now.plus(tokenConfig.getExpiration(), ChronoUnit.SECONDS);
		JwtClaimsSet claims = JwtClaimsSet.builder().issuer("com.ta2khu75").issuedAt(now).expiresAt(validity)
				.subject(response.getId())
				.claim("profileId", response.getProfile().getId())
				.claim("statusId",response.getStatus().getId())
				.claim("scope", "ROLE_" + response.getStatus().getRole().getName()).build();
		JwsHeader jwsHeader = JwsHeader.with(JwtProviderFactory.JWT_ALGORITHM).build();
		return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
	}

	public TokenResponse createRefreshToken(AccountResponse response) {
		Instant now = Instant.now();
		TokenConfig tokenConfig=jwtProperties.getConfigByType(TokenType.REFRESH);
		JwtEncoder jwtEncoder = jwtProviderFactory.getEncoder(TokenType.REFRESH);
		Instant validity = now.plus(tokenConfig.getExpiration(), ChronoUnit.SECONDS);
		JwtClaimsSet claims = JwtClaimsSet.builder().issuer("com.ta2khu75").issuedAt(now).expiresAt(validity)
				.subject(response.getId())
				.claim("statusId",response.getStatus().getId()).build();
		JwsHeader jwsHeader = JwsHeader.with(JwtProviderFactory.JWT_ALGORITHM).build();
		String token=jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
		return new TokenResponse(token, validity.toEpochMilli());
	}
	
	public Jwt validateToken(String token, TokenType type) {
		JwtDecoder jwtDecoder = jwtProviderFactory.getDecoder(type);
		return jwtDecoder.decode(token);
	}
	public <T> T getClaim( Jwt jwt,String claimName, Class<T> clazz) {
		T claim = jwt.getClaim(claimName);
		if (claim == null) {
			throw new UnAuthenticatedException("Missing claim: " + claimName);
		}
		return claim;
	}
}
