package com.ta2khu75.quiz.model.request.update;

import com.ta2khu75.quiz.model.ReportStatus;
import com.ta2khu75.quiz.model.entity.id.ReportId;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportStatusRequest {
	@NotNull
	ReportId id;
	@NotNull
	ReportStatus reportStatus;
}
