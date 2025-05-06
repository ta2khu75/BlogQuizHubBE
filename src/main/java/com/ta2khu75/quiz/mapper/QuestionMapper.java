package com.ta2khu75.quiz.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import com.ta2khu75.quiz.model.dto.QuestionDto;
import com.ta2khu75.quiz.model.entity.Question;

@Mapper(componentModel = "spring", uses = { AnswerMapper.class })
public interface QuestionMapper {
	@Named("toQuestion")
	@Mapping(target = "quiz", ignore = true)
	@Mapping(target = "userAnswers", ignore = true)
	@Mapping(target = "answers", source = "answers", qualifiedByName = "toAnswer")
	Question toEntity(QuestionDto dto);
	
	@Named("toQuestionDto")
	@Mapping(target = "answers", ignore = true)
	QuestionDto toDto(Question question);
	
	@Named("toQuestionDetailDto")
	@Mapping(target = "answers", source = "answers", qualifiedByName = "toAnswerDto")
	QuestionDto toDetailDto(Question question);
	
	@Named("toQuestionAnswerDetailDto")
	@Mapping(target = "answers", source = "answers", qualifiedByName = "toAnswerDetailDto")
	QuestionDto toQuestionAnswerDetailDto(Question question);
}
