package com.ta2khu75.quiz.event;

import org.springframework.context.ApplicationEvent;

import com.ta2khu75.quiz.model.TargetType;

import lombok.Getter;

@Getter
public class NotificationEvent extends ApplicationEvent {
	private static final long serialVersionUID = 8205739888714076152L;
	private TargetType targetType;
	private Long targetId;

	public NotificationEvent(Object source, Long targetId, TargetType targetType) {
		super(source);
		this.targetType= targetType;
		this.targetId = targetId;
	}
}
