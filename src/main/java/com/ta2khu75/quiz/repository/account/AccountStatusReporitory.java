package com.ta2khu75.quiz.repository.account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ta2khu75.quiz.model.entity.AccountStatus;

public interface AccountStatusReporitory extends JpaRepository<AccountStatus, Long> {
	Optional<AccountStatus> findByAccountId(String accountId);
}
