package com.ta2khu75.quiz.model.request.search;

import java.util.List;

import com.ta2khu75.quiz.model.AccessModifier;
import com.ta2khu75.quiz.model.QuizLevel;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = false)
public class QuizSearch extends Search {
    private List<QuizLevel> quizLevels;
    private List<Long> quizCategoryIds;
    private Integer minDuration;
	private Integer maxDuration;
	private String authorId;
	private Boolean completed;
	private AccessModifier accessModifier;
}
