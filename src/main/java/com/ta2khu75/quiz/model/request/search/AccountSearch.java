package com.ta2khu75.quiz.model.request.search;

import java.time.Instant;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AccountSearch extends Search {
	private Boolean nonLocked;
	private Boolean enabled;
	private Long roleId;
	private Instant createdFrom;
	private Instant createdTo;
}
