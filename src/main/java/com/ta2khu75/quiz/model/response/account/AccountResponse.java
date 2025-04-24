package com.ta2khu75.quiz.model.response.account;

import com.ta2khu75.quiz.model.response.InfoResponse;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AccountResponse {
	AccountProfileResponse profile;
	AccountStatusResponse status;
	InfoResponse<String> info;
}
