package com.ta2khu75.quiz.model.request;

import java.util.List;

import com.ta2khu75.quiz.model.base.QuizBase;
import com.ta2khu75.quiz.model.dto.QuestionDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuizRequest extends QuizBase{
	String blogId;
	@NotNull(message = "Quiz category must not be null")
	Long categoryId;
	@NotEmpty(message = "Question must not be empty")
	@Valid
	List<QuestionDto> questions;
}