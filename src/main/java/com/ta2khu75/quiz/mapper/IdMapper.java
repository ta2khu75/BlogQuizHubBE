package com.ta2khu75.quiz.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import com.ta2khu75.quiz.model.entity.base.BaseEntityLong;
import com.ta2khu75.quiz.model.entity.base.SaltedIdentifiable;
import com.ta2khu75.quiz.model.response.BaseResponse;
import com.ta2khu75.quiz.util.SqidsUtil;

@Mapper(componentModel = "spring")
public interface IdMapper {
	@Named("encodeId")
	default String encodeId(BaseEntityLong entity) {
		if (entity instanceof SaltedIdentifiable salted) {
			return SqidsUtil.encodeWithSalt(entity.getId(), salted.getSaltedType());
		}
		throw new IllegalArgumentException("Entity is not an instance of SaltedIdentifiable: " + entity.getClass().getName());
	}
	
	@Named("decodeId")
	default String encodeId(BaseResponse<Long> entity) {
		if (entity instanceof SaltedIdentifiable salted) {
			return SqidsUtil.encodeWithSalt(entity.getId(), salted.getSaltedType());
		}
		throw new IllegalArgumentException("Entity is not an instance of SaltedIdentifiable: " + entity.getClass().getName());
	}
	
	

	@Named("encode")
	default String encode(Long id, @Context SaltedIdentifiable salted) {
		return SqidsUtil.encodeWithSalt(id, salted.getSaltedType());
	}

	@Named("decode")
	default Long decode(String value, @Context SaltedIdentifiable salted) {
		return SqidsUtil.decodeWithSalt(value, salted.getSaltedType());
	}
}
