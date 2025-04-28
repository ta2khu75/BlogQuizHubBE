package com.ta2khu75.quiz.model.request;

import java.util.List;

import com.ta2khu75.quiz.anotation.ValidAnswer;
import com.ta2khu75.quiz.model.base.QuestionBase;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionRequest extends QuestionBase {
	@NotEmpty(message = "Answers must not be empty")
	@ValidAnswer
	List<AnswerRequest> answers;
}