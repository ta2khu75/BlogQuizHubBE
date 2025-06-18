package com.ta2khu75.quiz.service;


import com.ta2khu75.quiz.model.request.account.AccountPasswordRequest;
import com.ta2khu75.quiz.model.request.account.AccountProfileRequest;
import com.ta2khu75.quiz.model.request.account.AccountRequest;
import com.ta2khu75.quiz.model.request.account.AccountStatusRequest;
import com.ta2khu75.quiz.model.request.search.AccountSearch;
import com.ta2khu75.quiz.model.response.account.AccountProfileResponse;
import com.ta2khu75.quiz.model.response.account.AccountResponse;
import com.ta2khu75.quiz.model.response.account.AccountStatusResponse;
import com.ta2khu75.quiz.service.base.SearchService;

public interface AccountService extends SearchService<AccountResponse, AccountSearch>{
	AccountResponse create(AccountRequest request);
	AccountProfileResponse updateProfile(Long id,AccountProfileRequest request);
	AccountResponse updatePassword(String id,AccountPasswordRequest request);
    AccountStatusResponse updateStatus(Long id,AccountStatusRequest request);
    AccountProfileResponse readProfile(Long id);
	void delete(String id);
}
