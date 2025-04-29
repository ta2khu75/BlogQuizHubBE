package com.ta2khu75.quiz.model.response;

import java.io.Serializable;
import java.time.Instant;

public interface InfoResponse <ID extends Serializable>{
	ID getId();
	Instant getCreatedAt();
	Instant getUpdatedAt();
	void setId(ID id);
	void setCreatedAt(Instant createdAt);
	void setUpdatedAt(Instant updatedAt);
}
