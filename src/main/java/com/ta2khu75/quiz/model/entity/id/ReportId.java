package com.ta2khu75.quiz.model.entity.id;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class ReportId implements Serializable {
	private static final long serialVersionUID = -6196953391029199691L;
	private String authorId;
	private String targetId;
}