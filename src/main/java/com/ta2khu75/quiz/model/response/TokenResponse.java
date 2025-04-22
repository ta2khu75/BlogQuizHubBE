package com.ta2khu75.quiz.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenResponse {
	private String token;
	private Long expiration;
}
