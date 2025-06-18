package com.ta2khu75.quiz.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ta2khu75.quiz.model.entity.Account;
import com.ta2khu75.quiz.model.request.search.AccountSearch;

public interface AccountRepositoryCustom {
	Page<Account> search(AccountSearch search);
}
