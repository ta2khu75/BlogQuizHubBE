package com.ta2khu75.quiz.model.entity;

import com.ta2khu75.quiz.model.TargetType;
import com.ta2khu75.quiz.model.ReportStatus;
import com.ta2khu75.quiz.model.ReportType;
import com.ta2khu75.quiz.model.entity.base.EntityBaseCustom;
import com.ta2khu75.quiz.model.entity.id.ReportId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Report extends EntityBaseCustom<ReportId> {
	@ManyToOne
	@MapsId("authorId")
	AccountProfile author;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	ReportType reportType;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	TargetType targetType;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	ReportStatus reportStatus=ReportStatus.PENDING;
}
