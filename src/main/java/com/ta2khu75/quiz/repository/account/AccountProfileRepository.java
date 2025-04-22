package com.ta2khu75.quiz.repository.account;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ta2khu75.quiz.model.entity.AccountProfile;

public interface AccountProfileRepository extends JpaRepository<AccountProfile, Long>{
	Optional<AccountProfile> findByAccountId(String accountId);
}
