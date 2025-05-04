package com.ta2khu75.quiz.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.List;
import com.ta2khu75.quiz.model.AccessModifier;
import com.ta2khu75.quiz.model.QuizLevel;
import com.ta2khu75.quiz.model.QuizResultMode;
import com.ta2khu75.quiz.model.entity.base.BaseEntityLong;
import com.ta2khu75.quiz.model.entity.base.SaltedIdentifiable;
import com.ta2khu75.quiz.util.SaltedType;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = { "author", "blog", "questions", "category", "results" })
@EqualsAndHashCode(callSuper = true, exclude = { "author", "blog", "category", "questions", "results" })
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Quiz extends BaseEntityLong implements SaltedIdentifiable {
	@Column(nullable = false, columnDefinition = "NVARCHAR(MAX)")
	String title;
	@Column(nullable = false)
	Integer duration;
	@Column(nullable = false, columnDefinition = "NVARCHAR(MAX)")
	String description;
	@Column(nullable = false)
	String imagePath;
	boolean shuffleQuestion = true;
	boolean deleted;
	boolean completed;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	QuizLevel level;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	AccessModifier accessModifier = AccessModifier.PRIVATE;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	QuizResultMode quizResultMode = QuizResultMode.ANSWER_VISIBLE;
	@ManyToOne
	AccountProfile author;
	@ManyToOne
	Blog blog;
	@ManyToOne
	QuizCategory category;
	@OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
	List<Question> questions;
	@OneToMany(mappedBy = "quiz", cascade = CascadeType.REMOVE, orphanRemoval = true)
	List<QuizResult> results;
	@Override
	public SaltedType getSaltedType() {
		return SaltedType.QUIZ;
	}
}
