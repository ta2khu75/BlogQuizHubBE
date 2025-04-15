package com.ta2khu75.quiz.model.response;

import com.ta2khu75.quiz.model.ReportStatus;
import com.ta2khu75.quiz.model.base.ReportBase;
import com.ta2khu75.quiz.model.entity.id.ReportId;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportResponse extends ReportBase {
	InfoResponse<ReportId> info;
	AccountResponse author;
	ReportStatus reportStatus;
	Object target;
}
