package com.ta2khu75.quiz.model.response.account;

import java.time.Instant;

import com.ta2khu75.quiz.model.base.AccountStatusBase;
import com.ta2khu75.quiz.model.response.RoleResponse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountStatusResponse extends AccountStatusBase {
	Long id;
	RoleResponse role;
	Instant updatedAt;
	String updatedBy;
}