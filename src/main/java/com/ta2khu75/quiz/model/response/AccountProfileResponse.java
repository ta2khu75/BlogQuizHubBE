package com.ta2khu75.quiz.model.response;

import java.time.Instant;

import com.ta2khu75.quiz.model.base.AccountProfileBase;

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
public class AccountProfileResponse extends AccountProfileBase{
	Long id;
	String displayName;	
	int blogCount;
	int quizCount;
	int followCount;
	Instant updatedAt;
}
