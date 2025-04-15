package com.ta2khu75.quiz.model.request;

import com.ta2khu75.quiz.model.base.ReportBase;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportRequest extends ReportBase{
	@NotNull
	String targetId;
}
