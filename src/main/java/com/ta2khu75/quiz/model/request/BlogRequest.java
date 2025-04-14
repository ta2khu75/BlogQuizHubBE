package com.ta2khu75.quiz.model.request;


import java.util.Set;

import com.ta2khu75.quiz.model.base.BlogBase;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BlogRequest extends BlogBase {
	@NotNull(message = "Content must not be null")
	String content;
	Set<String> quizIds;
}
