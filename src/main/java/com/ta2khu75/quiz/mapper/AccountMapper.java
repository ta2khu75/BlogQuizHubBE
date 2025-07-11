package com.ta2khu75.quiz.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import com.ta2khu75.quiz.model.entity.Account;
import com.ta2khu75.quiz.model.entity.AccountProfile;
import com.ta2khu75.quiz.model.entity.AccountStatus;
import com.ta2khu75.quiz.model.request.account.AccountProfileRequest;
import com.ta2khu75.quiz.model.request.account.AccountStatusRequest;
import com.ta2khu75.quiz.model.response.PageResponse;
import com.ta2khu75.quiz.model.response.account.AccountProfileResponse;
import com.ta2khu75.quiz.model.response.account.AccountResponse;
import com.ta2khu75.quiz.model.response.account.AccountStatusResponse;

import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { RoleMapper.class })
public interface AccountMapper extends PageMapper<Account, AccountResponse>{
	@Mapping(target="birthday", source = "birthday")
	@Mapping(target="lastName", source = "lastName")
	@Mapping(target="firstName", source = "firstName")
	@BeanMapping(ignoreByDefault = true)
	AccountProfile toEntity(AccountProfileRequest request);
	
	@Mapping(target="nonLocked", source = "nonLocked")
	@Mapping(target="enabled", source = "enabled")
	
	@BeanMapping(ignoreByDefault = true)
	void update(AccountStatusRequest request, @MappingTarget AccountStatus entity);

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

	@Mapping(target = "blogCount", expression = "java(entity.getBlogs().size())")
	@Mapping(target = "quizCount", expression = "java(entity.getQuizzes().size())")
	@Mapping(target = "followCount", expression = "java(entity.getFollowers().size())")
	AccountProfileResponse toDetailProfileResponse(AccountProfile entity);

	@Mapping(target = "role", source = "role", qualifiedByName = "toRoleResponse")
	AccountStatusResponse toResponse(AccountStatus entity);
	
	
	@Named("toAccountResponse")
	@Mapping(target = "profile" , source = "profile", qualifiedByName = "toProfileResponse")
	AccountResponse toResponse(Account entity);

//	@Mapping(target = "page", source = "number")
//	@Mapping(target="content", source = "content", qualifiedByName = "toAccountResponse")
//	PageResponse<AccountResponse> toPageResponse(Page<Account> response);
}
