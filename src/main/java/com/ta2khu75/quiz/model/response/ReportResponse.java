package com.ta2khu75.quiz.model.response;

import com.ta2khu75.quiz.model.TargetType;
import com.ta2khu75.quiz.model.entity.id.ReportId;
import com.ta2khu75.quiz.model.ReportType;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportResponse {
	InfoResponse<ReportId> info;
	ReportType reportType;
	TargetType targetType;
	AccountResponse author;
	Object target;
}
