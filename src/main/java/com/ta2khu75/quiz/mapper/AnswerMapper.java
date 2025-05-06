package com.ta2khu75.quiz.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.ta2khu75.quiz.model.dto.AnswerDto;
import com.ta2khu75.quiz.model.entity.Answer;

@Mapper(componentModel = "spring")
public interface AnswerMapper {
	@Named("toAnswer")
	@Mapping(target = "question", ignore = true)
	@Mapping(target = "userAnswers", ignore = true)
	Answer toEntity(AnswerDto request);

	@Named("toAnswerDto")
	@Mapping(target = "correct", ignore = true)
	
	AnswerDto toDto(Answer answer);

	@Named("toAnswerDetailDto")
	AnswerDto toDetailDto(Answer answer);
}