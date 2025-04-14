package com.ta2khu75.quiz.model.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.ta2khu75.quiz.model.NotificationStatus;
import com.ta2khu75.quiz.model.TargetType;
import com.ta2khu75.quiz.model.entity.base.EntityBaseCustom;
import com.ta2khu75.quiz.model.entity.id.NotificationId;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
@EntityListeners(AuditingEntityListener.class)
public class Notification extends EntityBaseCustom<NotificationId> {
	@ManyToOne
	@MapsId("accountId")
	Account account;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	TargetType targetType;
	@Enumerated(EnumType.STRING)
	NotificationStatus status=NotificationStatus.UNREAD;
}
