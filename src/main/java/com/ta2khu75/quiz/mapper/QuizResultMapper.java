package com.ta2khu75.quiz.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import com.ta2khu75.quiz.model.entity.QuizResult;
import com.ta2khu75.quiz.model.response.QuizResultResponse;
import com.ta2khu75.quiz.model.response.PageResponse;

@Mapper(componentModel = "spring", uses = {  QuizMapper.class, AccountMapper.class, UserAnswerMapper.class })
public interface QuizResultMapper extends PageMapper<QuizResult, QuizResultResponse> {
	@Named("toQuizResultResponse")
//	@Mapping(target = "info", source = "quizResult", qualifiedByName = "toInfoResponse")
	@Mapping(target = "account", source = "account", qualifiedByName = "toProfileResponse")
	@Mapping(target = "quiz", source = "quiz", qualifiedByName = "toQuizDetailResponse")
	@Mapping(target = "userAnswers",ignore = true )
	QuizResultResponse toResponse(QuizResult quizResult);

//	@Mapping(target = "info", source = "quizResult", qualifiedByName = "toInfoResponse")
	@Mapping(target = "account", source = "account", qualifiedByName = "toProfileResponse")
	@Mapping(target = "quiz", source = "quiz", qualifiedByName = "toQuizQuestionDetailResponse")
	@Mapping(target = "userAnswers", source = "userAnswers", qualifiedByName = "toUserAnswerResponse")
	QuizResultResponse toResultResponse(QuizResult quizResult);

//	@Mapping(target = "info", source = "quizResult", qualifiedByName = "toInfoResponse")
//	@Mapping(target = "account", source = "account", qualifiedByName = "toAccountResponse")
//	@Mapping(target = "quiz", source = "quiz", qualifiedByName = "toQuizQuestionDetailResponse")
//	@Mapping(target = "userAnswers", source = "userAnswers", qualifiedByName = "toUserAnswerDetailResponse")
//	QuizResultResponse toAnswerResponse(QuizResult quizResult);

//	@Mapping(target = "info", source = "quizResult", qualifiedByName = "toInfoResponse")
	@Mapping(target = "account", source = "account", qualifiedByName = "toProfileResponse")
	@Mapping(target = "quiz", source = "quiz", qualifiedByName = "toQuizQuestionDetailResponse")
	@Mapping(target = "userAnswers", source = "userAnswers", qualifiedByName = "toUserAnswerDetailResponse")
	QuizResultResponse toDetailResponse(QuizResult quizResult);

//	@Mapping(target = "content", qualifiedByName = "toQuizResultResponse")
//	@Mapping(target = "page", source = "number")
//	PageResponse<QuizResultResponse> toPageResponse(Page<QuizResult> page);
}
