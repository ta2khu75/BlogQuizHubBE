package com.ta2khu75.quiz.mapper;

import java.util.function.Function;

import org.mapstruct.Mapper;
import org.mapstruct.Named;

import com.ta2khu75.quiz.model.entity.base.BaseEntityLong;
import com.ta2khu75.quiz.model.entity.base.BaseEntityString;
import com.ta2khu75.quiz.model.entity.base.SaltedIdentifiable;
import com.ta2khu75.quiz.model.response.BaseResponse;
import com.ta2khu75.quiz.util.Base62;
@Mapper(componentModel = "spring")
public interface IdMapper {
	@Named("encodeId")
    default String encodeId(BaseEntityLong entity) {
        if (entity instanceof SaltedIdentifiable salted) {
            return Base62.encodeWithSalt(entity.getId(), salted.getSaltedType());
        }
        return entity.getId().toString();
    }
	
	default Long decode(SaltedIdentifiable salted, String value) {
		return Base62.decodeWithSalt(value, salted.getSaltedType());
	}
	
//	@Named("decodeId")
//    default Long decodeId(BaseEntityString entity) {
//        if (entity instanceof SaltedIdentifiable salted) {
//            return Base62.decodeWithSalt(entity.getId(), salted.getSaltedType());
//        }
//        return null;
//    }
//	default Long decodeBlogId(String value) {
//		return Base62.decodeWithSalt(value, SaltedType.BLOG);
//	}
//	default Long decodeBlogQuizId(String value) {
//		return Base62.decodeWithSalt(value, SaltedType.QUIZ);
//	}
}
