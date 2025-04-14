package com.ta2khu75.quiz.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import com.ta2khu75.quiz.model.entity.Account;
import com.ta2khu75.quiz.model.request.AccountRequest;
import com.ta2khu75.quiz.model.request.update.AccountInfoRequest;
import com.ta2khu75.quiz.model.request.update.AccountStatusRequest;
import com.ta2khu75.quiz.model.response.AccountResponse;
import com.ta2khu75.quiz.model.response.ManagedAccountResponse;
import com.ta2khu75.quiz.model.response.PageResponse;
import com.ta2khu75.quiz.model.response.details.AccountDetailResponse;

import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { InfoMapper.class, RoleMapper.class })
public interface AccountMapper{
	@Named("toAccountResponse")
	@Mapping(target = "username", source = "displayName")
	@Mapping(target = "info", source = "account", qualifiedByName = "toInfoResponse")
	AccountResponse toResponse(Account account);
	
	@Mapping(target = "followers", ignore = true)
	@Mapping(target = "following", ignore = true)
	@Mapping(target = "displayName", ignore = true)
	@Mapping(target = "blogs", ignore = true)
	@Mapping(target = "codeVerify", ignore = true)
	@Mapping(target = "enabled", ignore = true)
	@Mapping(target = "quizzes", ignore = true)
	@Mapping(target = "nonLocked", ignore = true)
	@Mapping(target = "refreshToken", ignore = true)
	@Mapping(target = "role", ignore = true)
	Account toEntity(AccountRequest request);

	@Mapping(target = "username", source = "displayName")
	@Mapping(target = "info", source = "account", qualifiedByName = "toInfoResponse")
	@Mapping(target = "role", source = "role", qualifiedByName = "toRoleResponse")
	ManagedAccountResponse toManagedResponse(Account account);

	@Mapping(target = "username", source = "displayName")
	@Mapping(target = "info", source = "account", qualifiedByName = "toInfoResponse")
	@Mapping(target = "blogCount", expression = "java(account.getBlogs() != null ? account.getBlogs().size() : 0)")
	@Mapping(target = "quizCount", expression = "java(account.getQuizzes() != null ? account.getQuizzes().size() : 0)")
	@Mapping(target = "followCount", expression = "java(account.getFollowers() != null ? account.getFollowers().size() : 0)")
	AccountDetailResponse toDetailsResponse(Account account);

	@Mapping(target = "followers", ignore = true)
	@Mapping(target = "following", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "blogs", ignore = true)
	@Mapping(target = "codeVerify", ignore = true)
	@Mapping(target = "displayName", ignore = true)
	@Mapping(target = "enabled", ignore = true)
	@Mapping(target = "quizzes", ignore = true)
	@Mapping(target = "nonLocked", ignore = true)
	@Mapping(target = "refreshToken", ignore = true)
	@Mapping(target = "role", ignore = true)
	@Mapping(target = "authorities", ignore = true)
	void update(AccountRequest request, @MappingTarget Account account);

	@Mapping(target = "followers", ignore = true)
	@Mapping(target = "following", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "blogs", ignore = true)
	@Mapping(target = "codeVerify", ignore = true)
	@Mapping(target = "email", ignore = true)
	@Mapping(target = "enabled", ignore = true)
	@Mapping(target = "quizzes", ignore = true)
	@Mapping(target = "nonLocked", ignore = true)
	@Mapping(target = "password", ignore = true)
	@Mapping(target = "refreshToken", ignore = true)
	@Mapping(target = "role", ignore = true)
	@Mapping(target = "authorities", ignore = true)
	@Mapping(target = "displayName", source = "username")
	void update(AccountInfoRequest request, @MappingTarget Account account);

	@Mapping(target = "followers", ignore = true)
	@Mapping(target = "following", ignore = true)
	@Mapping(target = "createdAt", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "updatedAt", ignore = true)
	@Mapping(target = "birthday", ignore = true)
	@Mapping(target = "blogs", ignore = true)
	@Mapping(target = "codeVerify", ignore = true)
	@Mapping(target = "displayName", ignore = true)
	@Mapping(target = "email", ignore = true)
	@Mapping(target = "quizzes", ignore = true)
	@Mapping(target = "firstName", ignore = true)
	@Mapping(target = "lastName", ignore = true)
	@Mapping(target = "password", ignore = true)
	@Mapping(target = "refreshToken", ignore = true)
	@Mapping(target = "role", ignore = true)
	@Mapping(target = "authorities", ignore = true)
	void update(AccountStatusRequest request, @MappingTarget Account account);
	
	@Mapping(target = "page", source = "number")
	PageResponse<ManagedAccountResponse> toPageResponse(Page<Account> response);
}
