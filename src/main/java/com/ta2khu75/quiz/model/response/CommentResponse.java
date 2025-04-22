package com.ta2khu75.quiz.model.response;

import com.ta2khu75.quiz.model.base.CommentBase;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentResponse extends CommentBase {
	AccountProfileResponse author;
	InfoResponse<String> info;
}
