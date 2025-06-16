package com.ta2khu75.quiz.model.request.search;

import java.util.List;

import com.ta2khu75.quiz.model.AccessModifier;
import com.ta2khu75.quiz.model.QuizLevel;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = false)
public class QuizSearch extends Search {
    private List<QuizLevel> levels;
    private List<Long> categories;
    private Integer minDuration;
	private Integer maxDuration;
	private Long authorId;
	private Boolean completed;
	private AccessModifier accessModifier;
}
