package com.ta2khu75.quiz.model.response.account;

import java.time.Instant;

import com.ta2khu75.quiz.model.response.BaseResponse;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountResponse implements BaseResponse<String>{
	String id;
	Instant createdAt;
	Instant updatedAt;
	String  createdBy;
	AccountProfileResponse profile;
	AccountStatusResponse status;}
