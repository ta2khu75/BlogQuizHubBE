package com.ta2khu75.quiz.configuration;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

import com.ta2khu75.quiz.util.SecurityUtil;

public class ApplicationAuditAware implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		try {
			return Optional.ofNullable(SecurityUtil.getCurrentUserLogin().getUsername());
		} catch (Exception e) {
			return Optional.empty();
		}
	}

}
