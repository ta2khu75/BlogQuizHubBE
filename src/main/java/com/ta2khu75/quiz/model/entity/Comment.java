package com.ta2khu75.quiz.model.entity;

import com.ta2khu75.quiz.model.entity.base.BaseEntityString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment extends BaseEntityString {
	@Column(nullable = false, columnDefinition = "NVARCHAR(255)")
	String content;
	@ManyToOne
	AccountProfile author;
	@ManyToOne
	Blog blog;
}
