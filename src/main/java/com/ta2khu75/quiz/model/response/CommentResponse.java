package com.ta2khu75.quiz.model.response;

import java.time.Instant;

import com.ta2khu75.quiz.model.response.account.AccountProfileResponse;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentResponse  implements BaseResponse<String> {
	String id;
	String content;
	Instant createdAt;
	Instant updatedAt;
	AccountProfileResponse author;
}
