package com.ta2khu75.quiz.model.response;

import java.time.Instant;
import java.util.List;

import com.ta2khu75.quiz.model.response.account.AccountProfileResponse;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuizResultResponse implements BaseResponse<String>{
	String id;
	Instant createdAt;
	Instant updatedAt;
	Float point;
	Integer correctCount;
	QuizResponse quiz;
	AccountProfileResponse account;
	Instant endTime;
	List<UserAnswerResponse> userAnswers;
}
