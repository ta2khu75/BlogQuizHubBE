package com.ta2khu75.quiz.model.response;

import com.ta2khu75.quiz.model.entity.id.FollowId;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class FollowResponse {
	InfoResponse<FollowId> info;
	AccountResponse follower;
	AccountResponse following;
}
