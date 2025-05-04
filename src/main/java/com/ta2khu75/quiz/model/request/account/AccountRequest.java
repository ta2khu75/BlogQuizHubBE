package com.ta2khu75.quiz.model.request.account;

import com.ta2khu75.quiz.model.group.Admin;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountRequest {
	@NotBlank(message = "Email must not be blank")
	@Email(message = "Email should be a valid email address")
	String email;

	@NotBlank(message = "Password must not be blank")
	String password;

	@NotBlank(message = "Confirm password must not be blank")
	String confirmPassword;
	
	@NotNull(message = "Role id must not be null", groups = Admin.class)
	Long roleId;
	@Valid
	AccountProfileRequest profile;
}
