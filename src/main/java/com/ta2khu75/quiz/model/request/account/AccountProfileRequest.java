package com.ta2khu75.quiz.model.request.account;

import com.ta2khu75.quiz.model.base.AccountProfileBase;
import com.ta2khu75.quiz.model.group.Update;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountProfileRequest extends AccountProfileBase {
	@NotBlank(message = "Display name must not be blank", groups = Update.class)
	String displayName;
}
