package com.ta2khu75.quiz.model.response;

import com.ta2khu75.quiz.model.NotificationStatus;
import com.ta2khu75.quiz.model.TargetType;
import com.ta2khu75.quiz.model.entity.id.NotificationId;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NotificationResponse {
	InfoResponse<NotificationId> info;
	NotificationStatus status;
	Object target;
	TargetType targetType;
}
