package com.ta2khu75.quiz.mapper;

import org.mapstruct.Named;

import com.ta2khu75.quiz.model.entity.base.BaseEntity;
import com.ta2khu75.quiz.model.entity.base.SaltedIdentifiable;
import com.ta2khu75.quiz.model.response.BaseResponse;
import com.ta2khu75.quiz.util.Base62;

public interface BaseMapper<E extends BaseEntity<?>, RES extends BaseResponse<?>> {
//	@Named("toId")
//	default Object getId(E entity) {
//		if (entity instanceof SaltedIdentifiable salted) {
//			return Base62.encodeWithSalt((Long) entity.getId(), salted.getSaltedType());
//		}
//		return entity.getId();
//	}
}
