package com.ta2khu75.quiz.model.entity;

import com.ta2khu75.quiz.model.entity.base.EntityBaseString;

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
public class Comment extends EntityBaseString {
	@Column(nullable = false, columnDefinition = "NVARCHAR(255)")
	String content;
	@ManyToOne
	AccountProfile author;
	@ManyToOne
	Blog blog;
}
