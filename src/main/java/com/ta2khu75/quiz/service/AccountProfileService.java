package com.ta2khu75.quiz.service;

import com.ta2khu75.quiz.model.request.AccountProfileRequest;
import com.ta2khu75.quiz.model.response.AccountProfileResponse;

public interface AccountProfileService {
	AccountProfileResponse update(AccountProfileRequest request);
}
