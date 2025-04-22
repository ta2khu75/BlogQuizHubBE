package com.ta2khu75.quiz.model.request;

import com.ta2khu75.quiz.model.base.AccountProfileBase;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountProfileRequest extends AccountProfileBase {
	@NotBlank(message = "Display name must not be blank")
	String displayName;
}
