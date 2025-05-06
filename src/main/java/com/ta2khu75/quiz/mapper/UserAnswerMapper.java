package com.ta2khu75.quiz.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.ta2khu75.quiz.model.entity.UserAnswer;
import com.ta2khu75.quiz.model.response.UserAnswerResponse;

@Mapper(componentModel = "spring", uses = {AnswerMapper.class, QuestionMapper.class})
public interface UserAnswerMapper {
	@Named("toUserAnswerResponse")
	@Mapping(target = "answers", source = "answers", qualifiedByName = "toAnswerDto")
	@Mapping(target = "question", source = "question", qualifiedByName = "toQuestionDto")
	@Mapping(target = "correct", ignore = true)
	UserAnswerResponse toResponse(UserAnswer userAnswer);

	@Named("toUserAnswerDetailResponse")
	@Mapping(target = "answers", source = "answers", qualifiedByName = "toAnswerDto")
	@Mapping(target = "question", source = "question", qualifiedByName = "toQuestionDto")
	UserAnswerResponse toDetailResponse(UserAnswer userAnswer);
	
}
