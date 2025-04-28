package com.ta2khu75.quiz.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.ta2khu75.quiz.model.request.AnswerRequest;
import com.ta2khu75.quiz.model.response.AnswerResponse;
import com.ta2khu75.quiz.model.entity.Answer;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
	@Named("toAnswer")
	@Mapping(target = "question", ignore = true)
	@Mapping(target = "userAnswers", ignore = true)
	Answer toEntity(AnswerRequest request);

	@Named("toAnswerResponse")
	@Mapping(target = "correct", ignore = true)
	AnswerResponse toResponse(Answer answer);

	@Named("toAnswerDetailResponse")
	AnswerResponse toDetailResponse(Answer answer);
}