package com.ta2khu75.quiz.model.response;

import java.time.Instant;
import java.util.Set;

import com.ta2khu75.quiz.model.base.BlogBase;
import com.ta2khu75.quiz.model.response.account.AccountProfileResponse;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogResponse extends BlogBase implements InfoResponse<String> {
	String id;
	Instant createdAt;
	Instant updatedAt;
	int viewCount;
	int commentCount;

	AccountProfileResponse author;
	String content;
	Set<QuizResponse> quizzes;
}
