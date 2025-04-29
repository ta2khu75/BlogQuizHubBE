package com.ta2khu75.quiz.model.response;

import java.time.Instant;
import java.util.List;

import com.ta2khu75.quiz.model.base.QuizBase;
import com.ta2khu75.quiz.model.response.account.AccountProfileResponse;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuizResponse extends QuizBase implements InfoResponse<String> {
	String id;
	Instant createdAt;
	Instant updatedAt;
	String imagePath;
	AccountProfileResponse author;
	QuizCategoryResponse category;
//	InfoResponse<String> info;
	BlogResponse blog;
	List<QuestionResponse> questions;
}