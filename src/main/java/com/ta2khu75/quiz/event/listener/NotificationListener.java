package com.ta2khu75.quiz.event.listener;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.ta2khu75.quiz.event.NotificationEvent;
import com.ta2khu75.quiz.model.entity.Follow;
import com.ta2khu75.quiz.model.entity.Notification;
import com.ta2khu75.quiz.model.entity.id.NotificationId;
import com.ta2khu75.quiz.repository.FollowRepository;
import com.ta2khu75.quiz.repository.NotificationRepository;
import com.ta2khu75.quiz.util.SecurityUtil;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NotificationListener implements ApplicationListener<NotificationEvent> {
	NotificationRepository repository;
	FollowRepository followRepository;
	SimpMessagingTemplate messagingTemplate;

	@Override
	public void onApplicationEvent(@NonNull NotificationEvent event) {
		String accountId = SecurityUtil.getIdCurrentUserLogin();
		Set<Follow> followers = followRepository.findByFollowingId(accountId);
		Set<Notification> notificationSet = followers.stream().map(follow -> {
			Notification notification = new Notification();
			notification.setId(new NotificationId(follow.getId().getFollowerId(), event.getTargetId()));
			notification.setTargetType(event.getTargetType());
			notification.setAccount(follow.getFollower());
			return notification;
		}).collect(Collectors.toSet());
		List<Notification> notifications = repository.saveAll(notificationSet);
		notifications.forEach(notification -> {
			messagingTemplate.convertAndSendToUser(notification.getId().getAccountId(), "/queue/notifications", notification);
		});
	}
}
