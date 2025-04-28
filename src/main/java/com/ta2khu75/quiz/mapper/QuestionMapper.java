package com.ta2khu75.quiz.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

import com.ta2khu75.quiz.model.request.QuestionRequest;
import com.ta2khu75.quiz.model.response.QuestionResponse;
import com.ta2khu75.quiz.model.entity.Question;

@Mapper(componentModel = "spring", uses = { AnswerMapper.class })
public interface QuestionMapper {
	@Named("toQuestion")
	@Mapping(target = "filePath", ignore = true)
	@Mapping(target = "quiz", ignore = true)
	@Mapping(target = "userAnswers", ignore = true)
	@Mapping(target = "answers", source = "answers", qualifiedByName = "toAnswer")
	Question toEntity(QuestionRequest request);
	
	@Named("toQuestionResponse")
	@Mapping(target = "answers", ignore = true)
	QuestionResponse toResponse(Question question);
	
	@Named("toQuestionDetailResponse")
	@Mapping(target = "answers", source = "answers", qualifiedByName = "toAnswerResponse")
	QuestionResponse toDetailResponse(Question question);
	
	@Named("toQuestionAnswerDetailResponse")
	@Mapping(target = "answers", source = "answers", qualifiedByName = "toAnswerDetailResponse")
	QuestionResponse toQuestionAnswerDetailResponse(Question question);
}
