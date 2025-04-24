package com.ta2khu75.quiz.model.entity.id;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class NotificationId implements Serializable {
	private static final long serialVersionUID = 6760439381782529866L;
	private Long accountId;
	private String targetId;
}
