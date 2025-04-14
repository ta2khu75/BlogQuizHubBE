package com.ta2khu75.quiz.event.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.ta2khu75.quiz.event.RoleChangeEvent;
import com.ta2khu75.quiz.service.util.RedisUtil;
import com.ta2khu75.quiz.service.util.RedisUtil.NameModel;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleChangeListener implements ApplicationListener<RoleChangeEvent> {
	RedisUtil redisUtil;
	@Override
	public void onApplicationEvent(@NonNull RoleChangeEvent event) {
		redisUtil.create(NameModel.ROLE, event.getRole().getName(), event.getRole());
	}
}
