package com.ta2khu75.quiz.model.base;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountStatusBase {
	@NotNull(message = "Enabled must not be null")
	Boolean enabled;
	@NotNull(message = "Non locked must not be null")
	Boolean nonLocked;
}
