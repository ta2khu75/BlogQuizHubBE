package com.ta2khu75.quiz.util;

import java.util.Optional;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import com.ta2khu75.quiz.exception.UnAuthenticatedException;
import com.ta2khu75.quiz.model.entity.Account;

public final class SecurityUtil {
	private SecurityUtil() {
		throw new IllegalStateException("Utility class");
	}

	/**
	 * Get the login of the current user.
	 *
	 * @return the login of the current user.
	 */
	public static String getIdCurrentUserLogin() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		String accountId = extractPrincipal(securityContext.getAuthentication());
		if (accountId != null) {
			return accountId;
		}
		throw new UnAuthenticatedException("You must be login");
	}
	public static Account getCurrentUserLogin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
			throw new UnAuthenticatedException("You must be login");
		}
		return (Account) authentication.getPrincipal();
	}
	private static String extractAuthorities(Authentication authentication) {
		return authentication.getAuthorities().iterator().next().getAuthority().replace("ROLE_", "");
	}

	private static String extractPrincipal(Authentication authentication) {
		if (authentication.getPrincipal() instanceof Jwt jwt) {
			return jwt.getSubject();
		}
		return null;
	}

	/**
	 * Get the login of the current role.
	 *
	 * @return the login of the current role.
	 */
	public static String getCurrentRoleLogin() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		return extractAuthorities(securityContext.getAuthentication());
	}
	public static boolean isAuthor(String id) {
		try {
			String accountId = getIdCurrentUserLogin();
			return accountId.equals(id);
		} catch (Exception e) {
			return false;
		}
	}


	/**
	 * Get the JWT of the current user.
	 *
	 * @return the JWT of the current user.
	 */
	public static Optional<String> getCurrentUserJWT() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		return Optional.ofNullable(securityContext.getAuthentication())
				.filter(authentication -> authentication.getCredentials() instanceof String)
				.map(authentication -> (String) authentication.getCredentials());
	}
}
