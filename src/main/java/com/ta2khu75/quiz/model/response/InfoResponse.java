package com.ta2khu75.quiz.model.response;

import java.io.Serializable;
import java.time.Instant;

import lombok.Data;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InfoResponse <T extends Serializable>{
	T id;
	Instant createdAt;
	Instant updatedAt;
}
