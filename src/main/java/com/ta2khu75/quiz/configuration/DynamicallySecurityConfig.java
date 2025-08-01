package com.ta2khu75.quiz.configuration;

import java.util.function.Supplier;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import com.ta2khu75.quiz.exception.UnAuthenticatedException;
import com.ta2khu75.quiz.model.RoleDefault;
import com.ta2khu75.quiz.model.entity.AccountStatus;
import com.ta2khu75.quiz.model.entity.Role;
import com.ta2khu75.quiz.service.RoleService;
import com.ta2khu75.quiz.service.util.RedisUtil;
import com.ta2khu75.quiz.service.util.RedisUtil.NameModel;
import com.ta2khu75.quiz.util.SecurityUtil;

import jakarta.servlet.http.HttpServletRequest;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class DynamicallySecurityConfig implements AuthorizationManager<HttpServletRequest> {
	@NonFinal
	AntPathMatcher pathMatcher = new AntPathMatcher();
	RoleService roleService;
	RedisUtil redisUtil;

	private boolean isAdmin(String roleName) {
		return RoleDefault.ADMIN.name().equals(roleName);
	}

	private boolean isAllowedEndpoint(Role role, String requestUrl, String httpMethod) {
		
		return role.getPermissions().stream()
				.anyMatch(permission -> {boolean resultHppt=httpMethod.equals(permission.getHttpMethod().name());
						boolean pathResult= pathMatcher.match(permission.getPath(), requestUrl);
						return  resultHppt&&pathResult;
				});
	}

	@Override
	public AuthorizationDecision check(Supplier<Authentication> authentication, HttpServletRequest object) {
		String requestUrl = object.getRequestURI();
		String httpMethod = object.getMethod();
		try {
			Long statusId= SecurityUtil.getCurrentStatusId();
			AccountStatus status= null;
			status= redisUtil.read(NameModel.ACCOUNT_LOCK, statusId, AccountStatus.class);
			if (status!= null) {
				throw new AccessDeniedException("Account locked");
			}
		} catch (UnAuthenticatedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String roleName = SecurityUtil.getCurrentRole();
		if (isAdmin(roleName)) {
			return new AuthorizationDecision(true);
		}
		Role role = roleService.readByName(roleName);
		boolean isAllowed = isAllowedEndpoint(role, requestUrl, httpMethod);
		if (isAllowedEndpoint(role, requestUrl, httpMethod)) {
			return new AuthorizationDecision(true);
		}
		throw new AccessDeniedException("Access denied");
	}
}
