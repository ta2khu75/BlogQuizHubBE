package com.ta2khu75.quiz.service;

import java.util.List;

import com.ta2khu75.quiz.model.request.QuizRequest;
import com.ta2khu75.quiz.model.request.search.QuizSearch;
import com.ta2khu75.quiz.model.response.QuizResponse;
import com.ta2khu75.quiz.service.base.CrudFileService;
import com.ta2khu75.quiz.service.base.SearchService;

public interface QuizService extends CrudFileService<QuizRequest, QuizResponse, String>, SearchService<QuizResponse, QuizSearch> {
	QuizResponse readDetail(String id);
	List<QuizResponse> readAllByAuthorIdAndKeywork(Long authorId,String keyword);
//	List<QuizResponse> myReadAllById(List<String> ids);
//	Long countByAuthorEmail(String authorEmail);
//	Long countByAuthorIdAndAccessModifier(String authorId, AccessModifier accessModifier);
}
