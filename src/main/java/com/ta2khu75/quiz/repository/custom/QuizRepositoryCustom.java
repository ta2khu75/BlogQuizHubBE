package com.ta2khu75.quiz.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ta2khu75.quiz.model.entity.Quiz;
import com.ta2khu75.quiz.model.request.search.QuizSearch;

public interface QuizRepositoryCustom {
	Page<Quiz> search(QuizSearch search, Pageable pageable);
}
