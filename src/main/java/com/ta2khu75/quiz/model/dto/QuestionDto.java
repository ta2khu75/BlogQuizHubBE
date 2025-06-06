package com.ta2khu75.quiz.model.dto;

import java.util.List;

import com.ta2khu75.quiz.anotation.ValidAnswer;
import com.ta2khu75.quiz.model.QuestionType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record QuestionDto(Long id, @NotBlank(message = "Question must not be blank") String content, String imagePath,
		boolean shuffleAnswer, @NotNull(message = "Question type must not be null") QuestionType type,
		@NotEmpty(message = "Answers must not be empty") @ValidAnswer List<AnswerDto> answers) {
}
