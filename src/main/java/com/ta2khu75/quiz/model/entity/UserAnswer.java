package com.ta2khu75.quiz.model.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserAnswer {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	boolean correct;
	@ManyToOne
	QuizResult quizResult;
	@ManyToOne
	Question question;
	@ManyToMany
	List<Answer> answers;
}
