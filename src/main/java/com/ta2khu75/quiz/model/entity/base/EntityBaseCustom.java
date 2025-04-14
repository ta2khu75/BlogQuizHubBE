package com.ta2khu75.quiz.model.entity.base;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EntityBaseCustom<T extends Object> extends EntityBase  {
	@Id
	@EmbeddedId
	T id;
}
