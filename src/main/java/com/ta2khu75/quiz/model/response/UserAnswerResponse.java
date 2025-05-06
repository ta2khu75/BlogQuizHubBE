package com.ta2khu75.quiz.model.response;

import java.util.List;

import com.ta2khu75.quiz.model.dto.AnswerDto;
import com.ta2khu75.quiz.model.dto.QuestionDto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAnswerResponse {
	Long id;
	boolean correct;
	QuestionDto question;
	List<AnswerDto> answers;
}
