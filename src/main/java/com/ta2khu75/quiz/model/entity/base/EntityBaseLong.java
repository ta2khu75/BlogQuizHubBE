package com.ta2khu75.quiz.model.entity.base;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
public abstract class EntityBaseLong extends EntityBase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
}
