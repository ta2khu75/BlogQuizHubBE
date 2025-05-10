package com.ta2khu75.quiz.model.request.update;

import com.ta2khu75.quiz.model.ReportStatus;
import com.ta2khu75.quiz.model.dto.ReportIdDto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportStatusRequest {
	@NotNull
	@Valid
	ReportIdDto id;
	@NotNull
	ReportStatus status;
}
