package com.ta2khu75.quiz.service;


import com.ta2khu75.quiz.model.request.AccountCreateRequest;
import com.ta2khu75.quiz.model.request.AccountPasswordRequest;
import com.ta2khu75.quiz.model.request.AccountProfileRequest;
import com.ta2khu75.quiz.model.request.AccountStatusRequest;
import com.ta2khu75.quiz.model.request.search.AccountSearch;
import com.ta2khu75.quiz.model.response.AccountProfileResponse;
import com.ta2khu75.quiz.model.response.AccountResponse;
import com.ta2khu75.quiz.model.response.AccountStatusResponse;
import com.ta2khu75.quiz.service.base.SearchService;

public interface AccountService extends SearchService<AccountResponse, AccountSearch>{
	AccountResponse create(AccountCreateRequest request);
	AccountProfileResponse updateProfile(Long id,AccountProfileRequest request);
	AccountResponse updatePassword(String id,AccountPasswordRequest request);
    AccountStatusResponse updateStatus(Long id,AccountStatusRequest request);
    AccountProfileResponse readProfile(Long id);
	void delete(String id);
}
