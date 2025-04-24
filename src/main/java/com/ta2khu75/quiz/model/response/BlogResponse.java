package com.ta2khu75.quiz.model.response;

import java.util.Set;

import com.ta2khu75.quiz.model.base.BlogBase;
import com.ta2khu75.quiz.model.response.account.AccountProfileResponse;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogResponse extends BlogBase {
	int viewCount;
	int commentCount;
	
	AccountProfileResponse author;
	InfoResponse<String> info;
	String content;
	Set<QuizResponse> quizzes;
}
