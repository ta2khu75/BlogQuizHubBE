package com.ta2khu75.quiz.model.request;

import com.ta2khu75.quiz.model.base.AccountStatusBase;

import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Data
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountStatusRequest extends AccountStatusBase {
	@NotNull(message = "Role id must not be null")
	Long roleId;
}
