package com.ta2khu75.quiz.service;

import com.ta2khu75.quiz.model.request.AccountCreateRequest;
import com.ta2khu75.quiz.model.request.AccountPasswordRequest;
import com.ta2khu75.quiz.model.request.AuthRequest;
import com.ta2khu75.quiz.model.response.AccountResponse;
import com.ta2khu75.quiz.model.response.AuthResponse;

import jakarta.mail.MessagingException;

public interface AuthService {
	AuthResponse login(AuthRequest authRequest);

	AccountResponse register(AccountCreateRequest accountRequest) throws MessagingException;

	AccountResponse changePassword(AccountPasswordRequest request);

	AuthResponse refreshToken(String token);


	boolean verify(String code);

	void logout();

	boolean checkAdmin();
}
