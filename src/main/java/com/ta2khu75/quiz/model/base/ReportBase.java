package com.ta2khu75.quiz.model.base;

import com.ta2khu75.quiz.model.ReportType;
import com.ta2khu75.quiz.model.TargetType;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class ReportBase {
	@NotNull
	ReportType reportType;
	@NotNull
	TargetType targetType;
}
