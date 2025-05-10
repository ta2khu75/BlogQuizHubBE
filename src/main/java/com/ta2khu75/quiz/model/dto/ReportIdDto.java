package com.ta2khu75.quiz.model.dto;

import java.io.Serializable;

import com.ta2khu75.quiz.model.TargetType;
import com.ta2khu75.quiz.model.entity.base.SaltedIdentifiable;
import com.ta2khu75.quiz.util.SaltedType;

import jakarta.validation.constraints.NotNull;

public record ReportIdDto(@NotNull String targetId, @NotNull TargetType targetType, @NotNull Long authorId)
		implements SaltedIdentifiable, Serializable {

	@Override
	public SaltedType getSaltedType() {
		switch (targetType) {
		case QUIZ:
			return SaltedType.QUIZ;
		case BLOG:
			return SaltedType.BLOG;
		default:
			throw new IllegalArgumentException("Unexpected value: " + targetType);
		}
	}
}
