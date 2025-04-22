package com.ta2khu75.quiz.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import com.ta2khu75.quiz.model.entity.Account;
import com.ta2khu75.quiz.model.entity.AccountProfile;
import com.ta2khu75.quiz.model.entity.AccountStatus;
import com.ta2khu75.quiz.model.request.AccountCreateRequest;
import com.ta2khu75.quiz.model.request.AccountProfileRequest;
import com.ta2khu75.quiz.model.request.AccountStatusRequest;
import com.ta2khu75.quiz.model.response.AccountProfileResponse;
import com.ta2khu75.quiz.model.response.AccountResponse;
import com.ta2khu75.quiz.model.response.AccountStatusResponse;
import com.ta2khu75.quiz.model.response.PageResponse;

import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { InfoMapper.class, RoleMapper.class })
public interface AccountMapper{
	@Mapping(target="birthday", source = "birthday")
	@Mapping(target="lastName", source = "lastName")
	@Mapping(target="firstName", source = "firstName")
	@BeanMapping(ignoreByDefault = true)
	AccountProfile toProfileEntity(AccountCreateRequest request);
	
	@Mapping(target="birthday", source = "birthday")
	@Mapping(target="lastName", source = "lastName")
	@Mapping(target="firstName", source = "firstName")
	@Mapping(target="displayName", source = "displayName")
	@BeanMapping(ignoreByDefault = true)
	void update(AccountProfileRequest request, @MappingTarget AccountProfile entity);
	
	@Named("toProfileResponse")
	@Mapping(target = "blogCount", ignore = true)
	@Mapping(target = "quizCount", ignore = true)
	@Mapping(target = "followCount", ignore = true)
	AccountProfileResponse toResponse(AccountProfile entity);

	@Mapping(target = "blogCount", expression = "java(account.getBlogs() != null ? account.getBlogs().size() : 0)")
	@Mapping(target = "quizCount", expression = "java(account.getQuizzes() != null ? account.getQuizzes().size() : 0)")
	@Mapping(target = "followCount", expression = "java(account.getFollowers() != null ? account.getFollowers().size() : 0)")
	AccountProfileResponse toDetailProfileResponse(AccountProfile account);

	@Mapping(target = "role", source = "role", qualifiedByName = "toRoleResponse")
	AccountStatusResponse toResponse(AccountStatus entity);
	
	@Mapping(target="nonLocked", source = "nonLocked")
	@Mapping(target="enabled", source = "enabled")
	@BeanMapping(ignoreByDefault = true)
	void update(AccountStatusRequest request, @MappingTarget AccountStatus entity);
	
	@Named("toAccountResponse")
	@Mapping(target = "info", source = "entity", qualifiedByName = "toInfoResponse")
	AccountResponse toResponse(Account entity);

	@Mapping(target = "page", source = "number")
	@Mapping(target="content", source = "content", qualifiedByName = "toAccountResponse")
	PageResponse<AccountResponse> toPageResponse(Page<Account> response);
}
