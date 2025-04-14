package com.ta2khu75.quiz.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import com.ta2khu75.quiz.model.entity.Follow;
import com.ta2khu75.quiz.model.response.FollowResponse;
import com.ta2khu75.quiz.model.response.PageResponse;

@Mapper(componentModel = "spring", uses = { AccountMapper.class, InfoMapperFactory.class })
public interface FollowMapper  {
	@Mapping(target = "info", source = "entity")
	@Mapping(target = "follower", source = "follower", qualifiedByName = "toAccountResponse")
	@Mapping(target = "following", source = "following", qualifiedByName = "toAccountResponse")
	FollowResponse toResponse(Follow entity);

	@Mapping(target = "page", source = "number")
	PageResponse<FollowResponse> toPageResponse(Page<Follow> page);
}
