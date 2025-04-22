package com.ta2khu75.quiz.model.request.search;

import java.time.Instant;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = false)
public class QuizResultSearch extends Search {
	private Instant fromDate;
	private Instant toDate;
    private Set<Long> quizCategoryIds;
}
