package com.ta2khu75.quiz.service;

import com.ta2khu75.quiz.model.request.AuthRequest;
import com.ta2khu75.quiz.model.request.account.AccountPasswordRequest;
import com.ta2khu75.quiz.model.request.account.AccountProfileRequest;
import com.ta2khu75.quiz.model.request.account.AccountRequest;
import com.ta2khu75.quiz.model.response.AuthResponse;
import com.ta2khu75.quiz.model.response.account.AccountProfileResponse;

import jakarta.mail.MessagingException;

public interface AuthService {
	AuthResponse login(AuthRequest authRequest);

	void register(AccountRequest accountRequest) throws MessagingException;

	void changePassword(AccountPasswordRequest request);

	AccountProfileResponse changeProfile(AccountProfileRequest request);

	AuthResponse refreshToken(String token);


	boolean verify(String code);

	void logout();

	boolean checkAdmin();
}
