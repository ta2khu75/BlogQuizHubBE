package com.ta2khu75.quiz.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.ta2khu75.quiz.model.TokenConfig;
import com.ta2khu75.quiz.model.TokenType;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
	private TokenConfig access;
	private TokenConfig refresh;

	public TokenConfig getConfigByType(TokenType type) {
		return switch (type) {
		case ACCESS -> access;
		case REFRESH -> refresh;
		};
	}

	public String getSecretByType(TokenType type) {
		return getConfigByType(type).getSecret();
	}

	public long getExpirationByType(TokenType type) {
		return getConfigByType(type).getExpiration();
	}
}
