package com.ta2khu75.quiz.mapper;

import com.ta2khu75.quiz.model.entity.base.EntityBase;
import com.ta2khu75.quiz.model.response.InfoResponse;

public interface InfoMapper<E extends EntityBase<?>, RES extends InfoResponse<?>> {
	RES toResponse(E entity);
}
