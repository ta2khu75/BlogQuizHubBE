package com.ta2khu75.quiz.model.entity;

import com.ta2khu75.quiz.model.entity.base.EntityBaseCustom;
import com.ta2khu75.quiz.model.entity.id.FollowId;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Follow extends EntityBaseCustom<FollowId>{
	@ManyToOne
	@MapsId("followerId")
	Account follower;
	@ManyToOne
	@MapsId("followingId")
	Account following;
}