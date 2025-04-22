package com.ta2khu75.quiz.model.entity;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.ta2khu75.quiz.model.AccessModifier;
import com.ta2khu75.quiz.model.entity.base.EntityBaseString;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true, exclude = { "author", "quizzes", "comments", "tags" })
@FieldDefaults(level = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class Blog extends EntityBaseString {
	@Column(nullable = false, columnDefinition = "NVARCHAR(255)")
	String title;
	@Column(nullable = false, columnDefinition = "NVARCHAR(MAX)")
	String content;
	int viewCount;
	boolean deleted;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	AccessModifier accessModifier;

	@OneToMany(mappedBy = "blog", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	Set<Quiz> quizzes;
	@OneToMany(mappedBy = "blog", orphanRemoval = true, cascade = CascadeType.REMOVE)
	List<Comment> comments;
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	Set<BlogTag> tags;
	@ManyToOne
	AccountProfile author;

	public void addQuiz(Quiz quiz) {
		quizzes.add(quiz);
		quiz.setBlog(this);
	}

	public void removeQuiz(Quiz quiz) {
		quizzes.remove(quiz);
		quiz.setBlog(null);
	}
}
