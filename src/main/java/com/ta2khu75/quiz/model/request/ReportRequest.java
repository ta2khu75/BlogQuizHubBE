package com.ta2khu75.quiz.model.request;

import com.ta2khu75.quiz.model.TargetType;
import com.ta2khu75.quiz.model.ReportType;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportRequest {
	@NotNull
	String targetId;
	@NotNull
	ReportType reportType;
	@NotNull
	TargetType targetType;
}
